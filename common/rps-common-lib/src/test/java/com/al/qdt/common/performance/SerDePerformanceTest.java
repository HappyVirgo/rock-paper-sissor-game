package com.al.qdt.common.performance;

import com.al.qdt.common.base.DtoTests;
import com.al.qdt.common.base.ProtoTests;
import com.al.qdt.common.dto.GameDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DisplayName("Testing serialization / deserialization performance")
class SerDePerformanceTest implements DtoTests, ProtoTests {
    private static final long RUNS_NUMBER = 1_000_000L; // number of serde runs

    GameDto expectedGameJsonDto;
    com.al.qdt.rps.grpc.v1.dto.GameDto expectedGameProtoDto;

    @BeforeEach
    void setUp() {
        this.expectedGameJsonDto = createGameJsonDto();
        this.expectedGameProtoDto = createGameProtoDto();
    }

    @AfterEach
    void tearDown() {
        this.expectedGameJsonDto = null;
        this.expectedGameProtoDto = null;
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing GameDto serialization length")
    void serializationLengthTest() {
        final var jsonBytes = objectMapper.writeValueAsBytes(this.expectedGameJsonDto);
        final var jsonObjectLength = jsonBytes.length;
        log.info("GameDTO JSON: length : {} bytes", jsonObjectLength);
        final var protoBytes = this.expectedGameProtoDto.toByteArray();
        final var protoMessageLengthLength = protoBytes.length;
        log.info("GameDTO PROTO: length : {} bytes", protoMessageLengthLength);
        assertTrue(protoMessageLengthLength < jsonObjectLength);
    }

    @Test
    @DisplayName("Testing GameDto serialization / deserialization performance")
    void serializationDeserializationPerformanceTest() {
        final Runnable jsonSerDeTask = () -> {
            try {
                performObjectSerDe(expectedGameJsonDto, GameDto.class);
            } catch (Exception e) {
                log.error("Error occurred while performing json serialization / deserialization: ", e);
            }
        };
        final Runnable protoSerDeTask = () -> {
            try {
                performMessageSerDe(expectedGameProtoDto, com.al.qdt.rps.grpc.v1.dto.GameDto.parser());
            } catch (Exception e) {
                log.error("Error occurred while performing proto 3 message serialization / deserialization: ", e);
            }
        };
        final var jsonSerDeTime = runTask(jsonSerDeTask, RUNS_NUMBER);
        log.info("GameDTO JSON: Elapsed time for {} serde runs : {} ms", RUNS_NUMBER, jsonSerDeTime);
        final var protoSerDeTime = runTask(protoSerDeTask, RUNS_NUMBER);
        log.info("GameDTO PROTO: Elapsed time for {} serde runs : {} ms", RUNS_NUMBER, protoSerDeTime);
        assertTrue(protoSerDeTime < jsonSerDeTime);
    }

    /**
     * Utility method for running tasks.
     *
     * @param task  task
     * @param count number of runs
     * @return elapsed time
     */
    private static long runTask(Runnable task, long count) {
        final var start = System.currentTimeMillis();
        for (var i = 0; i < count; i++) {
            task.run();
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * Serializes and deserialize pojo.
     *
     * @param obj   object
     * @param clazz class type
     * @param <T> object type
     */
    @SneakyThrows({JsonProcessingException.class, IOException.class})
    private static <T extends Serializable> void performObjectSerDe(T obj, Class<T> clazz) {
        // serialize object
        final var bytes = objectMapper.writeValueAsBytes(obj);
        // deserialize object
        objectMapper.readValue(bytes, clazz);
    }

    /**
     * Serializes and deserializes proto 3 message.
     *
     * @param message proto3 message
     * @param parser  proto3 message parser
     * @param <T>     proto 3 message type
     */
    @SneakyThrows(InvalidProtocolBufferException.class)
    private static <T extends Message> void performMessageSerDe(T message, Parser<T> parser) {
        // serialize proto 3 message
        final var bytes = message.toByteArray();
        // deserialize proto 3 message
        parser.parseFrom(bytes);
    }
}
