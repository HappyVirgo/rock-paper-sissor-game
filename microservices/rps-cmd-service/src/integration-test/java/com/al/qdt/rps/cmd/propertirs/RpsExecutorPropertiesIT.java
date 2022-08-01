package com.al.qdt.rps.cmd.propertirs;

import com.al.qdt.common.properties.RpsExecutorProperties;
import com.al.qdt.rps.cmd.base.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration testing of the RpsExecutorProperties class")
@Tag(value = "common")
public class RpsExecutorPropertiesIT extends AbstractIntegrationTest {
    private static final int EXECUTOR_CORE_POOL_SIZE = 2;
    private static final int EXECUTOR_MAX_POOL_SIZE = 2;
    private static final int EXECUTOR_QUEUE_CAPACITY = 500;
    private static final String EXECUTOR_THREAD_NAME_PREFIX = "async-thread";

    @Autowired
    RpsExecutorProperties rpsExecutorProperties;

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.rpsExecutorProperties);
    }

    @Test
    @DisplayName("Testing injected properties")
    void propertiesTest() {
        assertEquals(EXECUTOR_CORE_POOL_SIZE, this.rpsExecutorProperties.getCorePoolSize());
        assertEquals(EXECUTOR_MAX_POOL_SIZE, this.rpsExecutorProperties.getMaxPoolSize());
        assertEquals(EXECUTOR_QUEUE_CAPACITY, this.rpsExecutorProperties.getQueueCapacity());
        assertEquals(EXECUTOR_THREAD_NAME_PREFIX, this.rpsExecutorProperties.getThreadNamePrefix());
    }
}
