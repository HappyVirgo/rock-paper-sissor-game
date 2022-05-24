package com.al.qdt.common.config;

import com.al.qdt.common.advices.GlobalRestExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GlobalRestExceptionHandler.class)
public class ErrorHandlingConfig {
}
