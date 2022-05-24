package com.al.qdt.cqrs.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing BaseEvent abstract class")
class BaseEventTest {
    static final UUID TEST_ID = UUID.fromString("748873ec-f887-4090-93ff-f8b8cbb34c7a");

    @Mock(answer = CALLS_REAL_METHODS)
    BaseEvent baseEvent;

    @Test
    @DisplayName("Testing getId() method")
    void getIdTest() {
        when(this.baseEvent.getId()).thenReturn(TEST_ID);

        assertEquals(TEST_ID, this.baseEvent.getId());
    }
}
