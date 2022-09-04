package com.al.qdt.common.infrastructure.config;

import com.al.qdt.common.infrastructure.properties.RpsApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Open API 3.0 configuration.
 */
@Configuration
@EnableConfigurationProperties(RpsApiProperties.class)
@RequiredArgsConstructor
public class OpenApiConfig {
    private final BuildProperties buildProperties;
    private final RpsApiProperties rpsApiProperties;

    @Bean
    public OpenAPI rpsOpenApi() {
        return new OpenAPI().info(new Info()
                .title(this.buildProperties.get("app.name"))
                .version(this.buildProperties.get("app.version"))
                .description(this.buildProperties.get("app.description"))
                .license(new License().name(this.rpsApiProperties.getLicenseName())
                        .url(this.rpsApiProperties.getLicenseUrl())))
                .servers(List.of(new Server().url(this.rpsApiProperties.getDev().getServer().getBaseUrl())
                                .description(this.rpsApiProperties.getDev().getServer().getDescription()),
                        new Server().url(this.rpsApiProperties.getProd().getServer().getBaseUrl())
                                .description(this.rpsApiProperties.getProd().getServer().getDescription())));
    }
}
