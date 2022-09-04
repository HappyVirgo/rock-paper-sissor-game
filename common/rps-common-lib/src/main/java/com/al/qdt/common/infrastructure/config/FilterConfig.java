package com.al.qdt.common.infrastructure.config;

import com.al.qdt.common.api.filters.UniqueRequestIdServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Custom filter configuration.
 */
@Configuration
public class FilterConfig {

    /**
     * Configure custom servlet filters.
     *
     * @return configured filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<UniqueRequestIdServletFilter> servletFilterFilterRegistrationBean() {
        final var filterRegistrationBean = new FilterRegistrationBean<UniqueRequestIdServletFilter>();
        final var uniqueRequestIdServletFilter = new UniqueRequestIdServletFilter();
        filterRegistrationBean.setFilter(uniqueRequestIdServletFilter);
        return filterRegistrationBean;
    }
}
