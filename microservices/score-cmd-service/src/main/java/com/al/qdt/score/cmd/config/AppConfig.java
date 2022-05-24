package com.al.qdt.score.cmd.config;

import com.al.qdt.common.config.*;
import com.al.qdt.common.kafka.KafkaProducerConfig;
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
