package com.al.qdt.common.config;

import com.al.qdt.common.infrastructure.queries.QueryDispatcherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

@Configuration
@ComponentScan(basePackageClasses = QueryDispatcherImpl.class)
public class AppQryConfig {

    /**
     * Setting up Etag.
     *
     * @return ShallowEtagHeaderFilter
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
