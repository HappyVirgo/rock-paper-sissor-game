package com.al.qdt.score.qry.propertirs

import com.al.qdt.common.properties.RpsKafkaProperties
import com.al.qdt.score.qry.base.AbstractIntegration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

@Title("Integration testing of the RpsKafkaProperties class")
class RpsKafkaPropertiesITSpec extends AbstractIntegration {
    static final DLT_TOPIC_NAME = 'DLT.SCORE.QRY'
    static final CONSUMER_GROUP_ID = 'score-consumer-group-0'
    static final DLT_PARTITION_COUNT = 1
    static final DLT_PARTITION_NUMBER = 0
    static final RETRIES_NUMBER = 2
    static final RETRIES_DELAY_INTERVAL = 1

    @Subject
    @Autowired
    RpsKafkaProperties rpsKafkaProperties

    def 'Testing property injections'() {
        expect:
        assert rpsKafkaProperties
    }

    def 'Testing injected properties'() {
        expect:
        DLT_TOPIC_NAME == rpsKafkaProperties.dlqTopicName
        CONSUMER_GROUP_ID == rpsKafkaProperties.consumerGroupId
        DLT_PARTITION_COUNT == rpsKafkaProperties.dlqPartitionCount
        DLT_PARTITION_NUMBER == rpsKafkaProperties.dlqPartitionNumber
        RETRIES_NUMBER == rpsKafkaProperties.retriesNumber
        RETRIES_DELAY_INTERVAL == rpsKafkaProperties.retriesDelayInterval
    }
}
