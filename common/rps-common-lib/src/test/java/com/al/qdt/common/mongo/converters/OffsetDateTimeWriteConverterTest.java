package com.al.qdt.common.mongo.converters;

import com.al.qdt.common.base.DateTimeConverterBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Date;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing OffsetDateTimeWriteConverter class")
class OffsetDateTimeWriteConverterTest extends DateTimeConverterBaseTest {
    final OffsetDateTimeWriteConverter offsetDateTimeWriteConverter = new OffsetDateTimeWriteConverter();

    @Test
    @DisplayName("Testing convert() method")
    void convertTest() {
        final var actualOffsetDateTime = OffsetDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, NANOSECOND, UTC);
        final var actualDate = this.offsetDateTimeWriteConverter.convert(actualOffsetDateTime);

        assertNotNull(actualDate);
        assertTrue(actualDate instanceof Date);
        assertEquals(MILLISECONDS_SINCE_JAN_1970, actualDate.getTime());
    }
}
