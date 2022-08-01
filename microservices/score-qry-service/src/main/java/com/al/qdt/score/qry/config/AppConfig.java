package com.al.qdt.score.qry.config;

import com.al.qdt.common.config.*;
import com.al.qdt.common.kafka.KafkaConsumerConfig;
import com.al.qdt.score.qry.repositories.ScoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * Configuration of the Score query microservice.
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
@EnableJpaRepositories(basePackageClasses = ScoreRepository.class)
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
}
