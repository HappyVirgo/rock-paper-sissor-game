package com.al.qdt.common.grpc.helpers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import lombok.experimental.UtilityClass;

/**
 * Utility class to work with proto messages.
 */
@UtilityClass
public class ProtoUtils {

    /**
     * Converts proto3 message to json.
     *
     * @param message proto 3 message to be converted
     * @param <T>     proto 3 message type
     * @return json string
     * @throws InvalidProtocolBufferException if error occurred while printing
     */
    public static <T extends Message> String toJson(T message) throws InvalidProtocolBufferException {
        return JsonFormat.printer()
                .includingDefaultValueFields()
                .print(message);
    }

    /**
     * Converts json string to proto3 message .
     *
     * @param json    json string to be converted
     * @param message proto3 message
     * @param <T>     proto 3 message type
     * @return proto3 message
     * @throws InvalidProtocolBufferException if error occurred while parsing
     */
    public static <T extends Message> T toProto(String json, T message) throws InvalidProtocolBufferException {
        final var builder = message.getDefaultInstanceForType().toBuilder();
        JsonFormat.parser()
                .ignoringUnknownFields()
                .merge(json, builder);
        return (T) builder.build();
    }
}
