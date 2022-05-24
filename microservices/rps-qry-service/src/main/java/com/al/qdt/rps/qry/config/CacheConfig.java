package com.al.qdt.rps.qry.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * Caching configuration.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    private static final String GAME_CACHE_NAME = "games";

    /**
     * Creates a {@link SimpleCacheManager} bean with the specified caches.
     *
     * @return simple cache manager bean
     */
    @Bean
    @Profile("dev")
    public CacheManager cacheManager() {
        final var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(new ConcurrentMapCache(GAME_CACHE_NAME)));

        // manually call initialize the caches as our SimpleCacheManager is not declared as a bean
        cacheManager.initializeCaches();

        return new TransactionAwareCacheManagerProxy(cacheManager);
    }
}
