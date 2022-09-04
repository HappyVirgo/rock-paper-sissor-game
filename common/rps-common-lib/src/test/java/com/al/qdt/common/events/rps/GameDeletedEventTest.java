package com.al.qdt.common.events.rps;

import com.al.qdt.common.domain.base.EventTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing GameDeletedEvent class")
@Tag(value = "event")
class GameDeletedEventTest implements EventTests {
    private static final String EXPECTED_JSON = "{\"id\":\"748873ec-f887-4090-93ff-f8b8cbb34c7a\"}";

    GameDeletedEvent expectedGameDeletedEvent;

    @BeforeEach
    void setUp() {
        this.expectedGameDeletedEvent = createGameDeletedEvent();
    }

    @AfterEach
    void tearDown() {
        this.expectedGameDeletedEvent = null;
    }

    @Test
    @DisplayName("Testing GameDeletedEvent properties")
    void GameDeletedEventPropertiesTest() {
        assertAll("Testing GameDeletedEvent",
                () -> assertAll("BaseEvent properties",
                        () -> assertEquals(TEST_UUID, this.expectedGameDeletedEvent.getId(), "Id didn't match!")
                )
        );
    }

    @SneakyThrows({JsonProcessingException.class, JSONException.class})
    @Test
    @DisplayName("Testing GameDeletedEvent serialization")
    void gameDeletedEventSerializationTest() {
        final var actualJson = this.objectMapper.writeValueAsString(this.expectedGameDeletedEvent);

        assertNotNull(actualJson);
        assertThat(actualJson, containsString("id"));
        assertThat(actualJson, not(containsString("game")));

        JSONAssert.assertEquals(EXPECTED_JSON, actualJson, true);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing GameDeletedEvent deserialization")
    void gameDeletedEventDeserializationTest() {
        final var actualGameDeletedEvent = this.objectMapper.readValue(EXPECTED_JSON, GameDeletedEvent.class);

        assertNotNull(actualGameDeletedEvent);
        assertEquals(TEST_UUID, actualGameDeletedEvent.getId());
    }

    @Test
    @DisplayName("Testing GameDeletedEvent equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualGameDeletedEvent = createGameDeletedEvent();

        assertTrue(this.expectedGameDeletedEvent.equals(actualGameDeletedEvent) &&
                actualGameDeletedEvent.equals(this.expectedGameDeletedEvent));
        assertEquals(this.expectedGameDeletedEvent.hashCode(), actualGameDeletedEvent.hashCode());
    }
}
