package com.al.qdt.common.kafka;


import com.al.qdt.common.properties.RpsKafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka topic configuration.
 */
@Configuration
@EnableConfigurationProperties(RpsKafkaProperties.class)
@RequiredArgsConstructor
public class KafkaTopicConfig {
    private final RpsKafkaProperties rpsKafkaProperties;

    /**
     * Creates Dead Letter Queue topic for the application.
     * Note: If the topic already exists, the bean will be ignored.
     *
     * @return topic
     * @see <a href="https://www.enterpriseintegrationpatterns.com/DeadLetterChannel.html">Dead Letter Channel Pattern</a>
     */
    @Bean
    public NewTopic deadLetterTopic() {
        return TopicBuilder.name(this.rpsKafkaProperties.getDlqTopicName())
                // use only one partition for infrequently used Dead Letter Topic
                .partitions(this.rpsKafkaProperties.getDlqPartitionCount())
                .build();
    }
}
