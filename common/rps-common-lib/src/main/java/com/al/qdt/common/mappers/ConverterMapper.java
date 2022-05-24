package com.al.qdt.common.mappers;

import com.al.qdt.rps.grpc.v1.common.Hand;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public interface ConverterMapper {

    default com.al.qdt.common.enums.Hand enumConverter(Hand hand) {
        return com.al.qdt.common.enums.Hand.valueOf(hand.getValueDescriptor().getName());
    }

    /**
     * Converts from {@link LocalDate} to {@link Timestamp}.
     *
     * @param localDate input date
     * @return converted date
     */
    default Timestamp fromLocalDate(LocalDate localDate) {
        final var instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    /**
     * Converts from {@link Timestamp} to {@link LocalDate}.
     *
     * @param timestamp input date
     * @return converted date
     */
    default LocalDate toLocalDate(Timestamp timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(),
                timestamp.getNanos()),
                ZoneId.of("UTC"))
                .toLocalDate();
    }
}
