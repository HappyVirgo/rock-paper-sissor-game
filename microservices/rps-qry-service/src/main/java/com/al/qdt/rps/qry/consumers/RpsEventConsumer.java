package com.al.qdt.rps.qry.consumers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.rps.qry.handlers.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_MESSAGE_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION_ID;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TIMESTAMP;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

/**
 * Consumer class responsible for reading events from the specified kafka topics.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RpsEventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "GamePlayedEvent",
            clientIdPrefix = "game-consumer-json-1",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload @Valid GamePlayedEvent event,
                        @Header(name = RECEIVED_MESSAGE_KEY) String key,
                        @Header(RECEIVED_PARTITION_ID) int partitionId,
                        @Header(RECEIVED_TOPIC) String topic,
                        @Header(RECEIVED_TIMESTAMP) long ts,
                        @Header(OFFSET) long offset,
                        Acknowledgment ack) {
        log.info("CONSUMER-JSON: Received game played event with id {} from topic {} with key {}, offset {} and timestamp {} from partition {} ",
                event.getId(), topic, key, offset, ts, partitionId);
        this.eventHandler.on(event);
        log.debug("Commit message {} immediately", event.getId());
        ack.acknowledge();
    }

    @KafkaListener(topics = "GameDeletedEvent",
            clientIdPrefix = "game-consumer-json-2",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload GameDeletedEvent event,
                        @Header(name = RECEIVED_MESSAGE_KEY) String key,
                        @Header(RECEIVED_PARTITION_ID) int partitionId,
                        @Header(RECEIVED_TOPIC) String topic,
                        @Header(RECEIVED_TIMESTAMP) long ts,
                        @Header(OFFSET) long offset,
                        Acknowledgment ack) {
        log.info("CONSUMER-JSON: Received game deleted event with id {} from topic {} with key {}, offset {} and timestamp {} from partition {} ",
                event.getId(), topic, key, offset, ts, partitionId);
        this.eventHandler.on(event);
        log.debug("Commit message {} immediately", event.getId());
        ack.acknowledge();
    }
}
