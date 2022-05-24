package com.al.qdt.common.events;

import com.al.qdt.common.base.AbstractTest;
import com.al.qdt.common.providers.EventArgsProvider;
import com.al.qdt.cqrs.events.BaseEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.al.qdt.common.helpers.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("Integration testing of the event classes deserialization with partial context loading")
class EventDeserializerIT extends AbstractTest {

    /**
     * Modification of the Spring context during the runtime.
     */
    @TestConfiguration
    static class TestConfig {
        static final boolean IS_AUTO_COMMIT_ENABLED = true;

        @Autowired
        EmbeddedKafkaBroker embeddedKafkaBroker;

        @Bean
        @VisibleForTesting
        ProducerFactory<String, Object> producerFactory() {
            final Map<String, Object> producerProps = KafkaTestUtils.producerProps(this.embeddedKafkaBroker);
            return new DefaultKafkaProducerFactory<>(producerProps,
                    new StringSerializer(),
                    new JsonSerializer<>());
        }

        @Bean
        @VisibleForTesting
        KafkaTemplate kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory(), IS_AUTO_COMMIT_ENABLED);
        }
    }

    static final boolean IS_AUTO_COMMIT_ENABLED = false;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaTemplate<String, ? extends BaseEvent> kafkaTemplate;

    @Test
    @DisplayName("Testing if all required dependencies in the context")
    void autowiredTest() {
        assertNotNull(this.embeddedKafkaBroker);
        assertNotNull(this.kafkaTemplate);
    }

    @Timeout(5)
    @ParameterizedTest(name = "{arguments}")
    @ArgumentsSource(EventArgsProvider.class)
    @DisplayName("Testing deserialization of events")
    <T extends BaseEvent> void eventDeserializationTest(String key, T event, Class<T> clazz) {
        this.deserialize(key, event, clazz);
    }

    /**
     * Key/Value deserialization testing.
     *
     * @param key   key to be deserialized
     * @param event event object to be deserialized
     * @param clazz event object class
     * @param <T>   event object type
     */
    @SneakyThrows(InterruptedException.class)
    private <T extends BaseEvent> void deserialize(String key, T event, Class<T> clazz) {
        final var id = UUID.randomUUID().toString();
        final var topic = INPUT_TOPIC_PREFIX + id;
        this.embeddedKafkaBroker.addTopics(topic);

        final var records = new LinkedBlockingQueue<ConsumerRecord<String, ? extends BaseEvent>>();

        // test consumer configuration
        final var consumerProps = KafkaTestUtils.consumerProps(id,
                String.valueOf(IS_AUTO_COMMIT_ENABLED),
                this.embeddedKafkaBroker);

        final var consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps,
                new StringDeserializer(), new JsonDeserializer<>(clazz));

        final var containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener((MessageListener<String, ? extends BaseEvent>) records::add);

        final var container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.start();

        ContainerTestUtils.waitForAssignment(container, this.embeddedKafkaBroker.getPartitionsPerTopic());

        final var message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .build();

        final var producerFuture = this.kafkaTemplate.send(message);

        try {
            producerFuture.get();
        } catch (InterruptedException e) {
            log.error(INTERRUPTION_EXCEPTION_MESSAGE);
            throw new KafkaException(INTERRUPTION_EXCEPTION_MESSAGE, e);
        } catch (ExecutionException e) {
            log.error(EXECUTION_EXCEPTION_MESSAGE);
            throw new KafkaException(EXECUTION_EXCEPTION_MESSAGE, e);
        }

        final var consumerRecord = records.poll(POLLING_INTERVAL, TimeUnit.MILLISECONDS);
        log.info("Received event: {}", consumerRecord);

        assertNotNull(consumerRecord);

        final var actualKey = consumerRecord.key();

        assertNotNull(actualKey);
        assertEquals(key, actualKey);

        final var actualEvent = consumerRecord.value();

        assertNotNull(actualEvent);
        assertEquals(event, actualEvent);
    }
}
