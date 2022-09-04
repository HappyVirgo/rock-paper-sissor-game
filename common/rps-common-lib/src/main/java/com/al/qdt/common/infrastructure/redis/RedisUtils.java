package com.al.qdt.common.infrastructure.redis;

import com.google.protobuf.Message;
import lombok.experimental.UtilityClass;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Utility class to work with Redis caching.
 */
@UtilityClass
public class RedisUtils {

    /**
     * Creates a {@link RedisCacheConfiguration} Redis cache configuration for json messages.
     *
     * @param cacheDurationMin cache duration in minutes
     * @return Redis cache configuration
     */
    public static RedisCacheConfiguration redisCacheConfiguration(long cacheDurationMin) {
        return redisCacheBaseConfiguration(cacheDurationMin)
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    /**
     * Creates a {@link RedisCacheConfiguration} Redis cache configuration for proto messages.
     *
     * @param clazz            proto 3 message class
     * @param cacheDurationMin cache duration in minutes
     * @param <T>              proto 3 message type
     * @return Redis cache configuration
     */
    public static <T extends Message> RedisCacheConfiguration redisProtoCacheConfiguration(Class<T> clazz, long cacheDurationMin) {
        return redisCacheBaseConfiguration(cacheDurationMin)
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new ProtobufSerializer<T>(clazz)));
    }

    /**
     * Creates a {@link RedisCacheConfiguration} Redis cache base configuration.
     *
     * @param cacheDurationMin cache duration in minutes
     * @return Redis cache base configuration
     */
    private static RedisCacheConfiguration redisCacheBaseConfiguration(long cacheDurationMin) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(cacheDurationMin)) // changing the cache TTL
                .disableCachingNullValues();
    }
}
