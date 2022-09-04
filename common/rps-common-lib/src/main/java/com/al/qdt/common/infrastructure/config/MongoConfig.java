package com.al.qdt.common.infrastructure.config;

import com.al.qdt.common.infrastructure.mongo.converters.OffsetDateTimeReadConverter;
import com.al.qdt.common.infrastructure.mongo.converters.OffsetDateTimeWriteConverter;
import com.al.qdt.common.infrastructure.mongo.converters.ZonedDateTimeReadConverter;
import com.al.qdt.common.infrastructure.mongo.converters.ZonedDateTimeWriteConverter;
import com.al.qdt.common.infrastructure.mongo.providers.OffsetDateTimeProvider;
import com.al.qdt.common.infrastructure.mongo.repositories.EventRepository;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.UuidRepresentation;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * MongoDB configuration.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackageClasses = OffsetDateTimeProvider.class)
@EnableMongoRepositories(basePackageClasses = EventRepository.class)
@EnableMongoAuditing(dateTimeProviderRef = "offsetDateTimeProvider")
public class MongoConfig extends AbstractMongoClientConfiguration {
    private final MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return this.mongoProperties.getDatabase();
    }

    /**
     * Register custom converters.
     *
     * @return reconfigured MongoCustomConversions bean
     */
    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new OffsetDateTimeReadConverter(),
                new OffsetDateTimeWriteConverter(),
                new ZonedDateTimeReadConverter(),
                new ZonedDateTimeWriteConverter()));
    }

    /**
     * Enable Optimistic Locking.
     * Optimistic locking requires to set the WriteConcern to ACKNOWLEDGE.
     *
     * @return reconfigured WriteConcernResolver bean
     */
    @Bean
    public WriteConcernResolver writeConcernResolver() {
        return action -> {
            log.info("Using Write Concern of Acknowledged");
            return WriteConcern.ACKNOWLEDGED;
        };
    }

    /**
     * Configures client settings.
     *
     * @param builder MongoClientSettings builder
     */
    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder
                .credential(MongoCredential.createCredential(this.mongoProperties.getUsername(),
                        this.mongoProperties.getAuthenticationDatabase(),
                        this.mongoProperties.getPassword()))
                .applyToClusterSettings(settings -> settings.hosts(singletonList(new ServerAddress(this.mongoProperties.getHost(),
                        this.mongoProperties.getPort()))))
                // Configures UUID Codecs.
                .uuidRepresentation(UuidRepresentation.STANDARD);
    }
}
