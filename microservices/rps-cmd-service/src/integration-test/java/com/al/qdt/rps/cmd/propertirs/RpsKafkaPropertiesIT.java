package com.al.qdt.rps.cmd.propertirs;

import com.al.qdt.common.properties.RpsKafkaProperties;
import com.al.qdt.rps.cmd.base.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration testing of the RpsKafkaProperties class")
@Tag(value = "common")
class RpsKafkaPropertiesIT extends AbstractIntegrationTest {
    private static final int EXPECTED_MAX_IN_FLIGHT_REQUEST_PER_CONNECTION = 1;
    private static final boolean IS_IDEMPOTENCE_ENABLED = true;

    @Autowired
    RpsKafkaProperties rpsKafkaProperties;

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.rpsKafkaProperties);
    }

    @Test
    @DisplayName("Testing injected properties")
    void propertiesTest() {
        assertEquals(EXPECTED_MAX_IN_FLIGHT_REQUEST_PER_CONNECTION, this.rpsKafkaProperties.getMaxInFlightRequestPerConnection());
        assertEquals(IS_IDEMPOTENCE_ENABLED, this.rpsKafkaProperties.getIsIdempotenceEnabled());
    }
}
