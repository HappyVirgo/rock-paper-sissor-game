package com.al.qdt.common.events;

import com.al.qdt.common.base.AbstractTest;
import com.al.qdt.common.providers.EventArgsProvider;
import com.al.qdt.cqrs.events.BaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.al.qdt.common.helpers.Constants.EXECUTION_EXCEPTION_MESSAGE;
import static com.al.qdt.common.helpers.Constants.INPUT_TOPIC_PREFIX;
import static com.al.qdt.common.helpers.Constants.INTERRUPTION_EXCEPTION_MESSAGE;
import static com.al.qdt.common.helpers.Constants.POLLING_INTERVAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("Integration testing of the event classes serialization with partial context loading ")
class EventSerializerIT extends AbstractTest {
    static final boolean IS_AUTO_COMMIT_ENABLED = true;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Testing if all required dependencies in the context")
    void autowiredTest() {
        assertNotNull(this.embeddedKafkaBroker);
    }

    @Timeout(5)
    @ParameterizedTest(name = "{arguments}")
    @ArgumentsSource(EventArgsProvider.class)
    @DisplayName("Testing serialization of events")
    <T extends BaseEvent> void eventSerializationTest(String key, T event, Class<T> clazz) {
        this.serialize(key, event, clazz);
    }

    /**
     * Key/Value serialization testing.
     *
     * @param key   key to be serialized
     * @param event event object to be serialized
     * @param clazz event object class
     * @param <T>   event object type
     */
    @SneakyThrows({InterruptedException.class, JsonProcessingException.class})
    private <T extends BaseEvent> void serialize(String key, T event, Class<T> clazz) {
        final var id = UUID.randomUUID().toString();
        final var topic = INPUT_TOPIC_PREFIX + id;
        this.embeddedKafkaBroker.addTopics(topic);

        final var records = new LinkedBlockingQueue<ConsumerRecord<String, Object>>();

        // test consumer configuration
        final var consumerProps = KafkaTestUtils.consumerProps(id,
                String.valueOf(IS_AUTO_COMMIT_ENABLED),
                this.embeddedKafkaBroker);

        final var consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps,
                new StringDeserializer(), new JsonDeserializer());

        final var containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener((MessageListener<String, Object>) records::add);

        final var container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.start();

        ContainerTestUtils.waitForAssignment(container, this.embeddedKafkaBroker.getPartitionsPerTopic());

        // test producer configuration
        final var producerProps = KafkaTestUtils.producerProps(this.embeddedKafkaBroker);
        final var keySerializer = new StringSerializer();
        final var serializer = new JsonSerializer<>();
        try (final var producer = new KafkaProducer<>(producerProps, keySerializer, serializer)) {
            final var producerFuture = producer.send(new ProducerRecord<>(topic, key, event));
            try {
                producerFuture.get();
            } catch (InterruptedException e) {
                log.error(INTERRUPTION_EXCEPTION_MESSAGE);
                throw new KafkaException(INTERRUPTION_EXCEPTION_MESSAGE, e);
            } catch (ExecutionException e) {
                log.error(EXECUTION_EXCEPTION_MESSAGE);
                throw new KafkaException(EXECUTION_EXCEPTION_MESSAGE, e);
            }
        }

        final var consumerRecord = records.poll(POLLING_INTERVAL, TimeUnit.MILLISECONDS);
        log.info("Received event: {}", consumerRecord);

        assertNotNull(consumerRecord);

        final var actualKey = consumerRecord.key();

        assertNotNull(actualKey);
        assertEquals(key, actualKey);

        final var actualEventData = consumerRecord.value();
        final var actualEvent = this.objectMapper.readValue(String.valueOf(actualEventData), clazz);

        assertNotNull(actualEvent);
        assertEquals(event, actualEvent);
    }
}
