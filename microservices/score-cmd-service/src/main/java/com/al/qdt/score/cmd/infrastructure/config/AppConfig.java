package com.al.qdt.score.cmd.infrastructure.config;

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
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Configuration of the Score command microservice.
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

    //    @Bean
    public OpenApiCustomiser openAPICustomiser(@Value("${api.dev.server.base-url}") String baseUrl) {
        return openApi -> openApi.getPaths()
                .values()
                .stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .filter(operation -> operation.getOperationId().equals("delete-by-id-json"))
                .forEach(operation ->
                        operation.setServers(Stream.of(new Server()
                                .url(baseUrl))
                                .collect(Collectors.toUnmodifiableList()))
                );
    }
}
