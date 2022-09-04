package com.al.qdt.rps.qry.infrastructure.config;

import com.al.qdt.common.infrastructure.queries.QueryDispatcherImpl;
import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.rps.qry.domain.repositories.GameRepository;
import com.al.qdt.rps.qry.infrastructure.handlers.QueryHandler;
import com.al.qdt.rps.qry.infrastructure.handlers.RpsQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for integration tests with partial context loading.
 * Modification of the Spring context during the runtime.
 */
@TestConfiguration
public class TestConfig {

    @Autowired
    GameRepository gameRepository;

    @Bean
    QueryDispatcher queryDispatcher() {
        return new QueryDispatcherImpl();
    }

    @Bean
    QueryHandler queryHandler() {
        return new RpsQueryHandler(gameRepository);
    }
}
