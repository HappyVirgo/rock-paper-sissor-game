package com.al.qdt.common.helpers;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * Utility class for decorating threads.
 */
public class AsyncTaskDecorator implements TaskDecorator {

    /**
     * Obtains the same MDC as on the main thread for async methods.
     *
     * @param runnable initial thread
     * @return decorated thread
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        final var contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(contextMap);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
