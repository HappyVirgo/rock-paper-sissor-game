package com.al.qdt.common.extensions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit extension class to measure integration test execution time.
 */
@Slf4j
public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final String START_TIME = "start time";
    private static final String LOG_MESSAGE = "Method [%s] took %s ms.";

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        this.getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        final var testMethod = context.getRequiredTestMethod();
        final var startTime = this.getStore(context).remove(START_TIME, long.class);
        final var duration = System.currentTimeMillis() - startTime;

        log.info(String.format(LOG_MESSAGE, testMethod.getName(), duration));
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }
}
