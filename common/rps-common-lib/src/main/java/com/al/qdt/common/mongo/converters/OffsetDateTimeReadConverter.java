package com.al.qdt.common.mongo.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {

    /**
     * Converts {@link Date} object to {@link OffsetDateTime}.
     * @param date date to be converted
     * @return converted date
     */
    @Override
    public OffsetDateTime convert(Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }
}
