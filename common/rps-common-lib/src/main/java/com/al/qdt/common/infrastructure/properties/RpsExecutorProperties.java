package com.al.qdt.common.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Async executor properties.
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "app.executor")
public class RpsExecutorProperties {
    private String threadNamePrefix; // thread name prefix

    private Integer corePoolSize; // core pool size

    private Integer maxPoolSize; // max pool size

    private Integer queueCapacity; // queue capacity
}
