package com.al.qdt.common.dto;

import com.al.qdt.common.base.DtoTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing GameDto class")
class GameDtoTest implements DtoTests {
    GameDto expectedGameDto;

    @BeforeEach
    void setUp() {
        this.expectedGameDto = createGameJsonDto();
    }

    @AfterEach
    void tearDown() {
        this.expectedGameDto = null;
    }

    @Test
    @DisplayName("Testing GameDto properties")
    void gameDtoPropertiesTest() {
        assertAll("Testing GameDto",
                () -> assertEquals(TEST_ID, this.expectedGameDto.getId(), "Id didn't match!"),
                () -> assertEquals(USERNAME_ONE, this.expectedGameDto.getUsername(), "Username didn't match!"),
                () -> assertEquals(ROCK.name(), this.expectedGameDto.getHand(), "Hand didn't match!")
        );
    }

    @SneakyThrows({JsonProcessingException.class, JSONException.class})
    @Test
    @DisplayName("Testing GameDto serialization")
    void gameDtoSerializationTest() {
        final var actualJson = objectMapper.writeValueAsString(this.expectedGameDto);

        assertNotNull(actualJson);
        assertThat(actualJson, containsString("id"));
        assertThat(actualJson, containsString("username"));
        assertThat(actualJson, containsString("hand"));
        assertThat(actualJson, not(containsString("game")));

        JSONAssert.assertEquals(GAME_EXPECTED_JSON, actualJson, true);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing GameDto deserialization")
    void gameDtoDeserializationTest() {
        final var actualGameDto = objectMapper.readValue(GAME_EXPECTED_JSON, GameDto.class);

        assertNotNull(actualGameDto);
        assertEquals(TEST_ID, actualGameDto.getId());
        assertEquals(this.expectedGameDto.getId(), actualGameDto.getId());
        assertEquals(USERNAME_ONE, actualGameDto.getUsername());
        assertEquals(this.expectedGameDto.getUsername(), actualGameDto.getUsername());
        assertEquals(ROCK.name(), actualGameDto.getHand());
        assertEquals(this.expectedGameDto.getHand(), actualGameDto.getHand());
    }

    @Test
    @DisplayName("Testing GameDto equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualGameDto = createGameJsonDto();

        assertTrue(this.expectedGameDto.equals(actualGameDto) &&
                actualGameDto.equals(this.expectedGameDto));
        assertEquals(this.expectedGameDto.hashCode(), actualGameDto.hashCode());
    }
}
