package com.al.qdt.common.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {

    /**
     * Allowing Micrometer to add a timer to custom methods.
     * <p>
     * This is required so that we can use the @Timed annotation
     * on methods that we want to time.
     * See: https://micrometer.io/docs/concepts#_the_timed_annotation
     *
     * @param registry MeterRegistry
     * @return TimedAspect
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    /**
     * Setting JVM thread metrics.
     *
     * @return JvmThreadMetrics
     */
    @Bean
    public JvmThreadMetrics threadMetrics() {
        return new JvmThreadMetrics();
    }
}
