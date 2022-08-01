package com.al.qdt.common.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Api properties.
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "api")
public class RpsApiProperties {
    private String licenseName; // license name

    private String licenseUrl; // license url

    private EnvProperties dev; // dev server properties

    private EnvProperties prod; // prod server properties

    /**
     * Environment properties.
     */
    @Setter
    @Getter
    @ToString
    public static class EnvProperties {
        private ServerProperties server;
    }

    /**
     * Server properties.
     */
    @Setter
    @Getter
    @ToString
    public static class ServerProperties {
        private String description; // server description

        private String baseUrl; // server base url
    }
}
