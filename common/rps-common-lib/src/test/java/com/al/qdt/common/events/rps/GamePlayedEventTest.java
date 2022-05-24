package com.al.qdt.common.events.rps;

import com.al.qdt.common.base.EventTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing GamePlayedEvent class")
class GamePlayedEventTest implements EventTests {
    private static final String EXPECTED_JSON = "{\"id\":\"748873ec-f887-4090-93ff-f8b8cbb34c7a\",\"username\":\"User1\",\"hand\":\"ROCK\"}";

    GamePlayedEvent expectedGamePlayedEvent;

    @BeforeEach
    void setUp() {
        this.expectedGamePlayedEvent = createGamePlayedEvent();
    }

    @AfterEach
    void tearDown() {
        this.expectedGamePlayedEvent = null;
    }

    @Test
    @DisplayName("Testing GamePlayedEvent properties")
    void GamePlayedEventPropertiesTest() {
        assertAll("Testing GamePlayedEvent",
                () -> assertAll("BaseEvent properties",
                        () -> assertEquals(TEST_UUID, this.expectedGamePlayedEvent.getId(), "Id didn't match!")
                ),
                () -> assertAll("GamePlayedEvent properties",
                        () -> assertEquals(USERNAME_ONE, this.expectedGamePlayedEvent.getUsername(), "Username didn't match!"),
                        () -> assertEquals(ROCK, this.expectedGamePlayedEvent.getHand(), "Hand didn't match!")
                )
        );
    }

    @SneakyThrows({JsonProcessingException.class, JSONException.class})
    @Test
    @DisplayName("Testing GamePlayedEvent serialization")
    void gamePlayedEventSerializationTest() {
        final var actualJson = this.objectMapper.writeValueAsString(this.expectedGamePlayedEvent);

        assertNotNull(actualJson);
        assertThat(actualJson, containsString("id"));
        assertThat(actualJson, containsString("username"));
        assertThat(actualJson, containsString("hand"));
        assertThat(actualJson, not(containsString("game")));

        JSONAssert.assertEquals(EXPECTED_JSON, actualJson, true);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing GamePlayedEvent deserialization")
    void gamePlayedEventDeserializationTest() {
        final var actualGamePlayedEvent = this.objectMapper.readValue(EXPECTED_JSON, GamePlayedEvent.class);

        assertNotNull(actualGamePlayedEvent);
        assertEquals(TEST_UUID, actualGamePlayedEvent.getId());
        assertEquals(USERNAME_ONE, actualGamePlayedEvent.getUsername());
        assertEquals(ROCK, actualGamePlayedEvent.getHand());
    }

    @Test
    @DisplayName("Testing GamePlayedEvent equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualGamePlayedEvent = createGamePlayedEvent();

        assertTrue(this.expectedGamePlayedEvent.equals(actualGamePlayedEvent) &&
                actualGamePlayedEvent.equals(this.expectedGamePlayedEvent));
        assertEquals(this.expectedGamePlayedEvent.hashCode(), actualGamePlayedEvent.hashCode());
    }
}
