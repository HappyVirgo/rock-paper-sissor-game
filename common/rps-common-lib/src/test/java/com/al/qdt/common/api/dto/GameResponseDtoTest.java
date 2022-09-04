package com.al.qdt.common.api.dto;

import com.al.qdt.common.domain.base.DtoTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.enums.Hand.SCISSORS;
import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.GAME_RESPONSE_EXPECTED_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing GameResponseDto class")
class GameResponseDtoTest implements DtoTests {
    GameResponseDto expectedGameResponseDto;

    @BeforeEach
    void setUp() {
        this.expectedGameResponseDto = createGameResponseJsonDto();
    }

    @AfterEach
    void tearDown() {
        this.expectedGameResponseDto = null;
    }

    @Test
    @DisplayName("Testing GameResponseDto properties")
    void GameResponseDtoPropertiesTest() {
        assertAll("Testing GameResponseDto",
                () -> assertEquals(ROCK.name(), this.expectedGameResponseDto.getUserChoice(), "User choice didn't match!"),
                () -> assertEquals(SCISSORS.name(), this.expectedGameResponseDto.getMachineChoice(), "Machine choice didn't match!"),
                () -> assertEquals(USER, this.expectedGameResponseDto.getResult(), "Game result didn't match!")
        );
    }

    @SneakyThrows({JsonProcessingException.class, JSONException.class})
    @Test
    @DisplayName("Testing GameResponseDto serialization")
    void gameResponseDtoSerializationTest() {
        final var actualJson = objectMapper.writeValueAsString(this.expectedGameResponseDto);

        assertNotNull(actualJson);
        assertThat(actualJson, containsString("user_choice"));
        assertThat(actualJson, containsString("machine_choice"));
        assertThat(actualJson, containsString("result"));
        assertThat(actualJson, not(containsString("game")));

        JSONAssert.assertEquals(GAME_RESPONSE_EXPECTED_JSON, actualJson, true);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing GameResponseDto deserialization")
    void gameResponseDtoDeserializationTest() {
        final var actualGameResponseDto = objectMapper.readValue(GAME_RESPONSE_EXPECTED_JSON, GameResponseDto.class);

        assertNotNull(actualGameResponseDto);
        assertEquals(ROCK.name(), actualGameResponseDto.getUserChoice());
        assertEquals(this.expectedGameResponseDto.getUserChoice(), actualGameResponseDto.getUserChoice());
        assertEquals(SCISSORS.name(), actualGameResponseDto.getMachineChoice());
        assertEquals(this.expectedGameResponseDto.getMachineChoice(), actualGameResponseDto.getMachineChoice());
        assertEquals(USER, actualGameResponseDto.getResult());
        assertEquals(this.expectedGameResponseDto.getResult(), actualGameResponseDto.getResult());
    }

    @Test
    @DisplayName("Testing GameResponseDto equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualGameResponseDto = createGameResponseJsonDto();

        assertTrue(this.expectedGameResponseDto.equals(actualGameResponseDto) &&
                actualGameResponseDto.equals(this.expectedGameResponseDto));
        assertEquals(this.expectedGameResponseDto.hashCode(), actualGameResponseDto.hashCode());
    }
}
