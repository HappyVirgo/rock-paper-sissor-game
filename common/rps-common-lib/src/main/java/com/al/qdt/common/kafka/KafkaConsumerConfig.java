package com.al.qdt.common.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import static org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE;

/**
 * Kafka consumer configuration.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    // delay interval between retries
    private static final long INTERVAL = 0L;
    // number of retries
    private static final long RETRIES_NUMBER = 2L;

    private final KafkaProperties kafkaProperties;

    /**
     * Creates Kafka consumer instances.
     *
     * @return kafka consumer factory
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.kafkaProperties.buildConsumerProperties());
    }

    /**
     * Creates containers for methods annotated with @KafkaListener annotation.
     *
     * @return kafka listener container factory
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>>
    kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(MANUAL_IMMEDIATE);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    /**
     * The DefaultErrorHandler considers certain exceptions to be fatal, and retries are skipped for such exceptions;
     * the recoverer is invoked on the first failure. The exceptions that are considered fatal, by default, are:
     * <p>
     * - DeserializationException
     * - MessageConversionException
     * - ConversionException
     * - MethodArgumentResolutionException
     * - NoSuchMethodException
     * - ClassCastException
     */
    @Bean
    public DefaultErrorHandler errorHandler() {
        final var defaultErrorHandler = new DefaultErrorHandler((record, exception) -> {
            // recover (skip) after 3 failures, with no back off - e.g. send to a dead-letter topic
            // failures are logged (level ERROR) after retries are exhausted
            log.info("Recovered: {}", record);
        }, new FixedBackOff(INTERVAL, RETRIES_NUMBER));
        //  adds IllegalArgumentException to the not-retryable exceptions:
        defaultErrorHandler.addNotRetryableExceptions(IllegalArgumentException.class);
        return defaultErrorHandler;
    }
}
