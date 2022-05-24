package com.al.qdt.common.mongo.converters;

import com.al.qdt.common.base.DateTimeConverterBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing OffsetDateTimeReadConverter class")
class OffsetDateTimeReadConverterTest extends DateTimeConverterBaseTest {
    final OffsetDateTimeReadConverter offsetDateTimeReadConverter = new OffsetDateTimeReadConverter();

    @Test
    @DisplayName("Testing convert() method")
    void convertTest() {
        final var actualOffsetDateTime = this.offsetDateTimeReadConverter.convert(expectedDate);

        assertNotNull(actualOffsetDateTime);
        assertTrue(actualOffsetDateTime instanceof OffsetDateTime);
        assertEquals(YEAR, actualOffsetDateTime.getYear());
        assertEquals(MONTH, actualOffsetDateTime.getMonth().getValue());
        assertEquals(DAY, actualOffsetDateTime.getDayOfMonth());
        assertEquals(HOUR, actualOffsetDateTime.getHour());
        assertEquals(MINUTE, actualOffsetDateTime.getMinute());
        assertEquals(SECOND, actualOffsetDateTime.getSecond());
        assertEquals(ZONE_OFFSET, actualOffsetDateTime.getOffset().getTotalSeconds());
    }
}
