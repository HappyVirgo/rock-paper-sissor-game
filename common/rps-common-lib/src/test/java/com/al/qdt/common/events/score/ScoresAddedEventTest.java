package com.al.qdt.common.events.score;

import com.al.qdt.common.base.EventTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing ScoresAddedEvent class")
class ScoresAddedEventTest implements EventTests {
    private static final String EXPECTED_JSON = "{\"id\":\"748873ec-f887-4090-93ff-f8b8cbb34c7a\",\"winner\":\"USER\"}";

    ScoresAddedEvent expectedScoresAddedEvent;

    @BeforeEach
    void setUp() {
        this.expectedScoresAddedEvent = createScoresAddedEvent();
    }

    @AfterEach
    void tearDown() {
        this.expectedScoresAddedEvent = null;
    }

    @Test
    @DisplayName("Testing ScoresAddedEvent properties")
    void ScoresAddedEventPropertiesTest() {
        assertAll("Testing ScoresAddedEvent",
                () -> assertAll("BaseEvent properties",
                        () -> assertEquals(TEST_UUID, this.expectedScoresAddedEvent.getId(), "Id didn't match!")
                ),
                () -> assertAll("ScoresAddedEvent properties",
                        () -> assertEquals(USER, this.expectedScoresAddedEvent.getWinner(), "Winner didn't match!")
                )
        );
    }

    @SneakyThrows({JsonProcessingException.class, JSONException.class})
    @Test
    @DisplayName("Testing ScoresAddedEvent serialization")
    void scoresAddedEventSerializationTest() {
        final var actualJson = this.objectMapper.writeValueAsString(this.expectedScoresAddedEvent);

        assertNotNull(actualJson);
        assertThat(actualJson, containsString("id"));
        assertThat(actualJson, containsString("winner"));
        assertThat(actualJson, not(containsString("score")));

        JSONAssert.assertEquals(EXPECTED_JSON, actualJson, true);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing ScoresAddedEvent deserialization")
    void scoresAddedEventDeserializationTest() {
        final var actualScoresAddedEvent = this.objectMapper.readValue(EXPECTED_JSON, ScoresAddedEvent.class);

        assertNotNull(actualScoresAddedEvent);
        assertEquals(TEST_UUID, actualScoresAddedEvent.getId());
        assertEquals(USER, actualScoresAddedEvent.getWinner());
    }

    @Test
    @DisplayName("Testing ScoresAddedEvent equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualScoresAddedEvent = createScoresAddedEvent();

        assertTrue(this.expectedScoresAddedEvent.equals(actualScoresAddedEvent) &&
                actualScoresAddedEvent.equals(this.expectedScoresAddedEvent));
        assertEquals(this.expectedScoresAddedEvent.hashCode(), actualScoresAddedEvent.hashCode());
    }
}
