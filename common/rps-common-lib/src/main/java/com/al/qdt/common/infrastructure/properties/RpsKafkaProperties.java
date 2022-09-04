package com.al.qdt.common.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Kafka properties.
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "app.kafka")
public class RpsKafkaProperties {
    private String dlqTopicName; // name of the dlq topic

    private String consumerGroupId; // consumer group id

    private Integer dlqPartitionCount; // number of partitions of the dlq topic

    private Integer dlqPartitionNumber; // dlq partition number

    private Integer maxInFlightRequestPerConnection; // max in flight request per connection, ms

    private Long retriesNumber; // number of retries

    private Long retriesDelayInterval; // delay interval between retries

    private Boolean isIdempotenceEnabled; // enables/disables idempotence
}
