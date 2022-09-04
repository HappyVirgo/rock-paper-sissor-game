package com.al.qdt.score.cmd.infrastructure.properties

import com.al.qdt.common.infrastructure.properties.RpsKafkaProperties
import com.al.qdt.score.cmd.base.AbstractIntegration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

@Title("Integration testing of the RpsKafkaProperties class")
class RpsKafkaPropertiesITSpec extends AbstractIntegration {
    static final int EXPECTED_MAX_IN_FLIGHT_REQUEST_PER_CONNECTION = 1
    static final boolean IS_IDEMPOTENCE_ENABLED = true

    @Subject
    @Autowired
    RpsKafkaProperties rpsKafkaProperties

    def 'Testing property injections'() {
        expect:
        assert rpsKafkaProperties
    }

    def 'Testing injected properties'() {
        expect:
        EXPECTED_MAX_IN_FLIGHT_REQUEST_PER_CONNECTION == rpsKafkaProperties.maxInFlightRequestPerConnection
        IS_IDEMPOTENCE_ENABLED == rpsKafkaProperties.isIdempotenceEnabled
    }
}
