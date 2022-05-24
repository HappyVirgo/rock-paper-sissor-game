package com.al.qdt.common.kafka;

import com.al.qdt.common.properties.RpsKafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

/**
 * Kafka producer configuration.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RpsKafkaProperties.class)
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private static final String RECORD_DELIVERED_OFFSET_MESSAGE = "Event {} was delivered with offset {}";
    private static final String RECORD_DELIVER_FAILED_MESSAGE = "Unable to deliver message {}, {}";

    private final KafkaProperties kafkaProperties;
    private final RpsKafkaProperties rpsKafkaProperties;

    /**
     * Creates Kafka producer factory instance.
     *
     * @return kafka producer factory
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        final var producerProperties = this.kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, this.rpsKafkaProperties.getMaxInFlightRequestPerConnection());
        producerProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, this.rpsKafkaProperties.getIsIdempotenceEnabled());
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    /**
     * Wraps a producer and provides convenience methods to send data to Kafka topics.
     *
     * @return KafkaTemplate instance
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        final var kafkaTemplate = new KafkaTemplate<>(this.producerFactory());
        kafkaTemplate.setProducerListener(new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<String, Object> producerRecord,
                                  RecordMetadata recordMetadata) {
                log.info(RECORD_DELIVERED_OFFSET_MESSAGE,
                        producerRecord.value(),
                        recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, Object> producerRecord,
                                RecordMetadata recordMetadata,
                                Exception exception) {
                log.error(RECORD_DELIVER_FAILED_MESSAGE,
                        producerRecord.value(),
                        exception.getMessage());
            }
        });
        return kafkaTemplate;
    }
}
