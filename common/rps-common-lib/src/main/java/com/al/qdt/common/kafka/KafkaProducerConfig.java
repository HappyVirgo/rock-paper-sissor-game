package com.al.qdt.common.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    /**
     * Creates Kafka producer instances.
     *
     * @return kafka producer factory
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(this.kafkaProperties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        final var kafkaTemplate = new KafkaTemplate<>(this.producerFactory());
        kafkaTemplate.setProducerListener(new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<String, Object> producerRecord,
                                  RecordMetadata recordMetadata) {
                log.info("Event {} was delivered with offset {}",
                        producerRecord.value(),
                        recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, Object> producerRecord,
                                RecordMetadata recordMetadata,
                                Exception exception) {
                log.error("Unable to deliver message {}, {}",
                        producerRecord.value(),
                        exception.getMessage());
            }
        });
        return kafkaTemplate;
    }
}
