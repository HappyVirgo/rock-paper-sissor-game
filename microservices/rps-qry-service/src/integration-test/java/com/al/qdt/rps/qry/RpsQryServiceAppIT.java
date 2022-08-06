package com.al.qdt.rps.qry;

import com.al.qdt.rps.qry.base.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Integration testing of the application context")
@Tag(value = "smoke")
class RpsQryServiceAppIT extends AbstractIntegrationTest {

    @Test
    @DisplayName("Testing of the application context")
    void contextLoads() {
        assertTrue(true);
    }
}
