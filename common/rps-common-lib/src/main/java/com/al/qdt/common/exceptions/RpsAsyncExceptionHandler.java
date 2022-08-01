package com.al.qdt.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Custom exception to handle exception for void return type, we need to create asynchronous exception handler
 * by implementing the AsyncUncaughtExceptionHandler interface.
 */
@Slf4j
public class RpsAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    public static final String EXCEPTION_WHILE_EXECUTING_WITH_MESSAGE = "Exception while executing with message {} ";
    public static final String EXCEPTION_HAPPEN_IN_METHOD = "Exception happen in {} method ";

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error(EXCEPTION_WHILE_EXECUTING_WITH_MESSAGE, throwable.getMessage());
        log.error(EXCEPTION_HAPPEN_IN_METHOD, method.getName());
    }
}
