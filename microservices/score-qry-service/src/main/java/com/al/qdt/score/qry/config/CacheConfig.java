package com.al.qdt.score.qry.config;

import com.al.qdt.rps.grpc.v1.dto.ScoreDto;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse;
import lombok.extern.slf4j.Slf4j;
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

import static com.al.qdt.common.redis.RedisUtils.redisCacheConfiguration;
import static com.al.qdt.common.redis.RedisUtils.redisProtoCacheConfiguration;

/**
 * Caching configuration.
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
    private static final String SCORE_CACHE_NAME = "score";
    private static final String SCORES_CACHE_NAME = "scores";
    private static final String SCORE_PROTO_CACHE_NAME = "scoreProto";
    private static final String SCORES_PROTO_CACHE_NAME = "scoresProto";
    private static final String WINNERS_CACHE_NAME = "winners";
    private static final String WINNERS_PROTO_CACHE_NAME = "winnersProto";
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
        cacheManager.setCaches(List.of(new ConcurrentMapCache(SCORE_CACHE_NAME),
                new ConcurrentMapCache(SCORES_CACHE_NAME),
                new ConcurrentMapCache(SCORE_PROTO_CACHE_NAME),
                new ConcurrentMapCache(SCORES_PROTO_CACHE_NAME),
                new ConcurrentMapCache(WINNERS_CACHE_NAME),
                new ConcurrentMapCache(WINNERS_PROTO_CACHE_NAME)));

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
                .withCacheConfiguration(SCORE_CACHE_NAME, redisCacheConfiguration(CACHE_DURATION_MIN))
                .withCacheConfiguration(SCORES_CACHE_NAME, redisCacheConfiguration(CACHE_DURATION_MIN))
                .withCacheConfiguration(WINNERS_CACHE_NAME, redisCacheConfiguration(CACHE_DURATION_MIN))
                .withCacheConfiguration(SCORE_PROTO_CACHE_NAME, redisProtoCacheConfiguration(ScoreDto.class, CACHE_DURATION_MIN))
                .withCacheConfiguration(SCORES_PROTO_CACHE_NAME, redisProtoCacheConfiguration(ListOfScoresResponse.class, CACHE_DURATION_MIN))
                .withCacheConfiguration(WINNERS_PROTO_CACHE_NAME, redisProtoCacheConfiguration(ListOfScoresResponse.class, CACHE_DURATION_MIN));
    }
}
