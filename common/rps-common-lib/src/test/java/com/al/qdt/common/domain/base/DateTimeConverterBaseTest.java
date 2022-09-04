package com.al.qdt.common.domain.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.util.Date;
import java.util.TimeZone;

@Tag(value = "converter")
public abstract class DateTimeConverterBaseTest {
    static final String TIME_ZONE = "UTC";
    protected static final long MILLISECONDS_SINCE_JAN_1970 = 1644159082000L;
    protected static final int YEAR = 2022;
    protected static final int MONTH = 2;
    protected static final int DAY = 6;
    protected static final int HOUR = 14;
    protected static final int MINUTE = 51;
    protected static final int SECOND = 22;
    protected static final int NANOSECOND = 0;
    protected static final int ZONE_OFFSET = 0;

    protected Date expectedDate;
    TimeZone expectedOriginalTimeZone;

    @BeforeEach
    void setUp() {
        this.expectedOriginalTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
        this.expectedDate = new Date(MILLISECONDS_SINCE_JAN_1970);
    }

    @AfterEach
    void tearDown() {
        TimeZone.setDefault(this.expectedOriginalTimeZone);
    }
}
