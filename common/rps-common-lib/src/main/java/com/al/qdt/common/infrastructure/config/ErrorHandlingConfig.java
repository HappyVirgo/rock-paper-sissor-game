package com.al.qdt.common.infrastructure.config;

import com.al.qdt.common.api.advices.GlobalRestExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GlobalRestExceptionHandler.class)
public class ErrorHandlingConfig {
}
