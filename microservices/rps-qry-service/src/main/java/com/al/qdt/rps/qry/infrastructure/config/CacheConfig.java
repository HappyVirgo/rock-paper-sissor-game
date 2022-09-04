package com.al.qdt.rps.qry.infrastructure.config;

import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.services.ListOfGamesResponse;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.util.List;

import static com.al.qdt.common.infrastructure.redis.RedisUtils.redisCacheConfiguration;
import static com.al.qdt.common.infrastructure.redis.RedisUtils.redisProtoCacheConfiguration;

/**
 * Caching configuration.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    public static final String GAME_CACHE_NAMES = "gamesCache";
    public static final String GAME_CACHE_NAME = "game";
    public static final String GAMES_CACHE_NAME = "games";
    public static final String GAME_PROTO_CACHE_NAME = "gameProto";
    public static final String GAMES_PROTO_CACHE_NAME = "gamesProto";
    public static final String USERNAME_CACHE_NAME = "username";
    public static final String USERNAME_PROTO_CACHE_NAME = "usernameProto";
    private static final long CACHE_DURATION_MIN = 60L; // cache time to live (TTL)

    /**
     * Creates a {@link SimpleCacheManager} bean with the specified caches.
     *
     * @return simple cache manager bean
     */
    @Bean
    @Profile("it")
    public CacheManager cacheManager() {
        final var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(new ConcurrentMapCache(GAME_CACHE_NAME),
                new ConcurrentMapCache(GAMES_CACHE_NAME),
                new ConcurrentMapCache(GAME_PROTO_CACHE_NAME),
                new ConcurrentMapCache(GAMES_PROTO_CACHE_NAME),
                new ConcurrentMapCache(USERNAME_CACHE_NAME),
                new ConcurrentMapCache(USERNAME_PROTO_CACHE_NAME)));

        // manually call initialize the caches as our SimpleCacheManager is not declared as a bean
        cacheManager.initializeCaches();

        return new TransactionAwareCacheManagerProxy(cacheManager);
    }

    /**
     * Creates a custom {@link RedisCacheConfiguration} Redis cache configuration.
     *
     * @return custom Redis cache configuration
     */
    @Bean
    @Profile("!it")
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration(GAME_CACHE_NAME, redisCacheConfiguration(CACHE_DURATION_MIN))
                .withCacheConfiguration(GAMES_CACHE_NAME, redisCacheConfiguration(CACHE_DURATION_MIN))
                .withCacheConfiguration(USERNAME_CACHE_NAME, redisCacheConfiguration(CACHE_DURATION_MIN))
                .withCacheConfiguration(GAME_PROTO_CACHE_NAME, redisProtoCacheConfiguration(GameDto.class, CACHE_DURATION_MIN))
                .withCacheConfiguration(GAMES_PROTO_CACHE_NAME, redisProtoCacheConfiguration(ListOfGamesResponse.class, CACHE_DURATION_MIN))
                .withCacheConfiguration(USERNAME_PROTO_CACHE_NAME, redisProtoCacheConfiguration(ListOfGamesResponse.class, CACHE_DURATION_MIN));
    }
}
