package com.al.qdt.common.infrastructure.producers;

import com.al.qdt.cqrs.events.BaseEvent;
import com.al.qdt.cqrs.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Producer class responsible for writing out events to the specified kafka topics.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Writes out an event to kafka topic.
     *
     * @param topic kafka topic
     * @param key   key
     * @param event event to be written out
     */
    @Override
    public void produce(String topic, String key, BaseEvent event) {
        log.info("Sending event with id {} and key {} to topic {}", event.getId(), key, topic);
        this.kafkaTemplate.send(topic, key, event);
    }
}
