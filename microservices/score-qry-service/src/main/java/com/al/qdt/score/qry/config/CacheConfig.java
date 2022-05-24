package com.al.qdt.score.qry.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Caching configuration.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    private static final String SCORE_CACHE_NAME = "scores";

    /**
     * Creates a {@link SimpleCacheManager} bean with the specified caches.
     *
     * @return simple cache manager bean
     */
    @Bean
    public CacheManager cacheManager() {
        final var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(new ConcurrentMapCache(SCORE_CACHE_NAME)));

        // manually call initialize the caches as our SimpleCacheManager is not declared as a bean
        cacheManager.initializeCaches();

        return new TransactionAwareCacheManagerProxy(cacheManager);
    }
}
