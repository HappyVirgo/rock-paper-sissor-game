package com.al.qdt.rps.cmd.infrastructure.config;

import com.al.qdt.common.infrastructure.config.AppCmdConfig;
import com.al.qdt.common.infrastructure.config.AsyncConfig;
import com.al.qdt.common.infrastructure.config.ErrorHandlingConfig;
import com.al.qdt.common.infrastructure.config.FilterConfig;
import com.al.qdt.common.infrastructure.config.MicrometerConfig;
import com.al.qdt.common.infrastructure.config.MongoConfig;
import com.al.qdt.common.infrastructure.config.OpenApiConfig;
import com.al.qdt.common.infrastructure.config.ProtoConfig;
import com.al.qdt.common.infrastructure.config.WebMvcCmdConfig;
import com.al.qdt.common.infrastructure.kafka.KafkaProducerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration of the RPS command microservice.
 */
@Configuration
@Import({AppCmdConfig.class,
        AsyncConfig.class,
        FilterConfig.class,
        WebMvcCmdConfig.class,
        OpenApiConfig.class,
        MongoConfig.class,
        KafkaProducerConfig.class,
        ProtoConfig.class,
        ErrorHandlingConfig.class,
        MicrometerConfig.class
})
public class AppConfig {
}
