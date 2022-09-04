package com.al.qdt.rps.qry.infrastructure.config;

import com.al.qdt.common.infrastructure.config.AppQryConfig;
import com.al.qdt.common.infrastructure.config.AsyncConfig;
import com.al.qdt.common.infrastructure.config.ErrorHandlingConfig;
import com.al.qdt.common.infrastructure.config.FilterConfig;
import com.al.qdt.common.infrastructure.config.MicrometerConfig;
import com.al.qdt.common.infrastructure.config.OpenApiConfig;
import com.al.qdt.common.infrastructure.config.ProtoConfig;
import com.al.qdt.common.infrastructure.config.WebMvcQryConfig;
import com.al.qdt.common.infrastructure.kafka.KafkaConsumerConfig;
import com.al.qdt.rps.qry.domain.repositories.GameRepository;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * Configuration of the RPS query microservice.
 */
@Configuration
@Import({AppQryConfig.class,
        AsyncConfig.class,
        FilterConfig.class,
        WebMvcQryConfig.class,
        OpenApiConfig.class,
        KafkaConsumerConfig.class,
        ProtoConfig.class,
        ErrorHandlingConfig.class,
        MicrometerConfig.class
})
@EnableJpaRepositories(basePackageClasses = GameRepository.class)
@EnableTransactionManagement
public class AppConfig {

    /**
     * Creates jpa transaction manager.
     *
     * @param entityManagerFactory entity manager factory
     * @return jpa transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * Modifies flyway migration strategy.
     *
     * @return flyway migration strategy
     */
    //    @Bean
    @Profile("dev")
    FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            flyway.clean(); // reset flyway history
            flyway.migrate();
        };
    }
}
