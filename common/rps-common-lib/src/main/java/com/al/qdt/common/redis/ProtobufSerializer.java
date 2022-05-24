package com.al.qdt.common.redis;

import com.google.protobuf.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class ProtobufSerializer<T extends Message> implements RedisSerializer<T> {

    private static final String PARSE_FROM_METHOD_NAME = "parseFrom";
    private static final Map<Class<?>, Method> METHOD_CACHE = new ConcurrentHashMap<>();

    private final Class<T> clazz;

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return (t == null) ? null : t.toByteArray();
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        T type = null;
        if (bytes != null) {
            try {
                type = parseFrom(clazz, bytes);
            } catch (Exception e) {
                log.error("An error occurred while deserializing proto 3 message.");
            }
        }
        return type;
    }

    /**
     * Create a new {@code Message.Builder} instance for the given class.
     * <p>Notes: This method uses a ConcurrentHashMap for caching method lookups.
     *
     * @param clazz proto 3 class
     * @param bytes byte array
     * @return proto 3 message
     * @throws Exception
     */
    private T parseFrom(Class<? extends Message> clazz, byte[] bytes) throws Exception {
        Method method = METHOD_CACHE.get(clazz);
        if (method == null) {
            method = clazz.getMethod(PARSE_FROM_METHOD_NAME, byte[].class);
            METHOD_CACHE.put(clazz, method);
        }
        return (T) method.invoke(clazz, bytes);
    }
}
