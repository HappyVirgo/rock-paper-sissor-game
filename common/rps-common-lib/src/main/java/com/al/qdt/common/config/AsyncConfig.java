package com.al.qdt.common.config;

import com.al.qdt.common.exceptions.RpsAsyncExceptionHandler;
import com.al.qdt.common.helpers.AsyncTaskDecorator;
import com.al.qdt.common.properties.RpsExecutorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Async method configuration.
 */
@Configuration
@EnableConfigurationProperties(RpsExecutorProperties.class)
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig {

    public static final String ASYNC_TASK_EXECUTOR = "ASYNC-TASK-EXECUTOR";

    private final RpsExecutorProperties rpsExecutorProperties;

    /**
     * Configure Async executor.
     *
     * @return executor
     */
    @Bean(name = ASYNC_TASK_EXECUTOR)
    public Executor asyncExecutor() {
        final var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.rpsExecutorProperties.getCorePoolSize());
        taskExecutor.setMaxPoolSize(this.rpsExecutorProperties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(this.rpsExecutorProperties.getQueueCapacity());
        taskExecutor.setThreadNamePrefix(this.rpsExecutorProperties.getThreadNamePrefix());
        taskExecutor.setTaskDecorator(new AsyncTaskDecorator());
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * Handles exceptions for void return type.
     *
     * @return custom exception handler
     */
    @Bean
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return new RpsAsyncExceptionHandler();
    }
}
