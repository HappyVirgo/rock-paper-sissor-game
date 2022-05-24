package com.al.qdt.score.qry.config

import com.al.qdt.common.infrastructure.queries.QueryDispatcherImpl
import com.al.qdt.cqrs.infrastructure.QueryDispatcher
import com.al.qdt.score.qry.handlers.QueryHandler
import com.al.qdt.score.qry.handlers.ScoreQueryHandler
import com.al.qdt.score.qry.repositories.ScoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

/**
 * Test configuration for integration tests with partial context loading.
 * Modification of the Spring context during the runtime.
 */
@TestConfiguration
class TestConfig {

    @Autowired
    ScoreRepository scoreRepository

    @Bean
    QueryDispatcher queryDispatcher() {
        new QueryDispatcherImpl()
    }

    @Bean
    QueryHandler queryHandler() {
        new ScoreQueryHandler(scoreRepository)
    }

//    @Bean
//    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        new JpaTransactionManager(entityManagerFactory)
//    }
}
