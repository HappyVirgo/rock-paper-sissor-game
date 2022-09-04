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

import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.SCORE_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.TEST_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing ScoreDto class")
class ScoreDtoTest implements DtoTests {
    ScoreDto expectedScoreDto;

    @BeforeEach
    void setUp() {
        this.expectedScoreDto = createScoreJsonDto();
    }

    @AfterEach
    void tearDown() {
        this.expectedScoreDto = null;
    }

    @Test
    @DisplayName("Testing ScoreDto properties")
    void scoreDtoPropertiesTest() {
        assertAll("Testing ScoreDto",
                () -> assertEquals(TEST_ID, this.expectedScoreDto.getId(), "Id didn't match!"),
                () -> assertEquals(USER.name(), this.expectedScoreDto.getWinner(), "Winner didn't match!")
        );
    }

    @SneakyThrows({JsonProcessingException.class, JSONException.class})
    @Test
    @DisplayName("Testing ScoreDto serialization")
    void scoreDtoSerializationTest() {
        final var actualJson = objectMapper.writeValueAsString(this.expectedScoreDto);

        assertNotNull(actualJson);
        assertThat(actualJson, containsString("id"));
        assertThat(actualJson, containsString("winner"));
        assertThat(actualJson, not(containsString("score")));

        JSONAssert.assertEquals(SCORE_EXPECTED_JSON, actualJson, true);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Test
    @DisplayName("Testing ScoreDto deserialization")
    void scoreDtoDeserializationTest() {
        final var actualScoreDto = objectMapper.readValue(SCORE_EXPECTED_JSON, ScoreDto.class);

        assertNotNull(actualScoreDto);
        assertEquals(TEST_ID, actualScoreDto.getId());
        assertEquals(this.expectedScoreDto.getId(), actualScoreDto.getId());
        assertEquals(USER.name(), actualScoreDto.getWinner());
        assertEquals(this.expectedScoreDto.getWinner(), actualScoreDto.getWinner());
    }

    @Test
    @DisplayName("Testing ScoreDto equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualScoreDto = createScoreJsonDto();

        assertTrue(this.expectedScoreDto.equals(actualScoreDto) &&
                actualScoreDto.equals(this.expectedScoreDto));
        assertEquals(this.expectedScoreDto.hashCode(), actualScoreDto.hashCode());
    }
}
