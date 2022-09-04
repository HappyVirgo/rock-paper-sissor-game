package com.al.qdt.common.infrastructure.config;

import com.google.protobuf.util.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

/**
 * Protobuf configuration.
 */
@Configuration
public class ProtoConfig {

    /**
     * Converts proto messages to json.
     *
     * @return converter
     */
    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        final var parser = JsonFormat.parser()
                .ignoringUnknownFields();
        final var printer = JsonFormat.printer()
                .includingDefaultValueFields();
        return new ProtobufJsonFormatHttpMessageConverter(parser, printer);
    }
}
