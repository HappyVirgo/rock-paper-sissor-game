package com.al.qdt.common.kafka;

import com.al.qdt.common.properties.RpsKafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.LogIfLevelEnabled;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.util.backoff.FixedBackOff;

import javax.validation.ValidationException;
import java.util.UUID;

import static org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE;

/**
 * Kafka consumer configuration.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RpsKafkaProperties.class)
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaProperties kafkaProperties;
    private final RpsKafkaProperties rpsKafkaProperties;

    /**
     * Creates Kafka consumer factory instances.
     *
     * @return kafka consumer factory
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        final var consumerProperties = this.kafkaProperties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,
                String.format("%s-%s", this.rpsKafkaProperties.getConsumerGroupId(), UUID.randomUUID().toString()));
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    /**
     * Creates containers for methods annotated with @KafkaListener annotation.
     *
     * @param consumerFactory consumer factory
     * @return kafka listener container factory
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>>
    kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {
        final var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.getContainerProperties().setAckMode(MANUAL_IMMEDIATE);
        // changing logging level of topic offset commits tp INFO
        factory.getContainerProperties().setCommitLogLevel(LogIfLevelEnabled.Level.INFO);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    /**
     * Specify a global error handler used for all listeners in the container factory.
     * The container will commit any pending offset commits before calling the error handler.
     * <p>
     * The DefaultErrorHandler considers certain exceptions to be fatal, and retries are skipped for such exceptions;
     * the recoverer is invoked on the first failure. The exceptions that are considered fatal, by default, are:
     * <p>
     * - DeserializationException
     * - MessageConversionException
     * - ConversionException
     * - MethodArgumentResolutionException
     * - NoSuchMethodException
     * - ClassCastException
     *
     * @param kafkaTemplate KafkaTemplate instance
     * @return error handler
     */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        // publish to dead letter topic any messages dropped after retries with back off
        final var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                // always send to first/only partition of DLT suffixed topic
                (cr, e) -> new TopicPartition(this.rpsKafkaProperties.getDlqTopicName(), this.rpsKafkaProperties.getDlqPartitionNumber()));
        final var defaultErrorHandler = new DefaultErrorHandler(recoverer,
                new FixedBackOff(this.rpsKafkaProperties.getRetriesDelayInterval(),
                        this.rpsKafkaProperties.getRetriesNumber()));
        // add IllegalArgumentException to the not-retryable exceptions
        // also do not try to recover from validation exceptions when validation failed
        defaultErrorHandler.addNotRetryableExceptions(IllegalArgumentException.class,
                NullPointerException.class,
                ClassCastException.class,
                NoSuchMethodException.class,
                DeserializationException.class,
                MessageConversionException.class,
                MethodArgumentResolutionException.class,
                ValidationException.class);
        // set the commitRecovered property to true
        // when the container is configured with AckMode.MANUAL_IMMEDIATE, the error handler can be configured to commit the offset of recovered records
        defaultErrorHandler.setCommitRecovered(Boolean.TRUE);
        return defaultErrorHandler;
    }
}
