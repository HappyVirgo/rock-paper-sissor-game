package com.al.qdt.rps.cmd.config;

import com.al.qdt.common.config.*;
import com.al.qdt.common.kafka.KafkaProducerConfig;
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
