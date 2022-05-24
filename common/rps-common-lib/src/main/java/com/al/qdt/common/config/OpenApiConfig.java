package com.al.qdt.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Open API 3.0 configuration.
 */
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {
    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI rpsOpenApi(@Value("${api.license-name}") String licenseName,
                              @Value("${api.license-url}") String licenseUrl,
                              @Value("${api.dev.server.description}") String devServerDescription,
                              @Value("${api.dev.server.base-url}") String devBaseUrl,
                              @Value("${api.prod.server.description}") String prodServerDescription,
                              @Value("${api.prod.server.base-url}") String prodBaseUrl) {
        return new OpenAPI().info(new Info()
                .title(this.buildProperties.get("app.name"))
                .version(this.buildProperties.get("app.version"))
                .description(this.buildProperties.get("app.description"))
                .license(new License().name(licenseName)
                        .url(licenseUrl)))
                .servers(List.of(new Server().url(devBaseUrl)
                                .description(devServerDescription),
                        new Server().url(prodBaseUrl)
                                .description(prodServerDescription)));
    }
}
