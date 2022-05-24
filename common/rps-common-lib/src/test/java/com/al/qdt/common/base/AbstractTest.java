package com.al.qdt.common.base;

import com.al.qdt.common.extensions.TimingExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Abstract class for kafka integration tests, allows to reduce the amount of test context restarts.
 */
@ExtendWith({SpringExtension.class, TimingExtension.class})
@EmbeddedKafka(partitions = 1)
@DirtiesContext
@ActiveProfiles("it")
@Tag("kafka")
public abstract class AbstractTest {
}
