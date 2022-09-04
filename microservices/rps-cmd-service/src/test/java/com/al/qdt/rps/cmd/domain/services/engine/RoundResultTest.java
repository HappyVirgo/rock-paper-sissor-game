package com.al.qdt.rps.cmd.domain.services.engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.enums.Player.USER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing RoundResult class")
@Tag(value = "engine")
class RoundResultTest {
    RoundResult expectedRoundResult;

    @BeforeEach
    void setUp() {
        this.expectedRoundResult = RoundResult.builder()
                .machineChoice(ROCK)
                .winner(USER)
                .build();
    }

    @AfterEach
    void tearDown() {
        this.expectedRoundResult = null;
    }

    @Test
    @DisplayName("Testing RoundResult properties")
    void roundResultPropertiesTest() {
        assertAll("Testing RoundResult",
                () -> assertEquals(ROCK, this.expectedRoundResult.getMachineChoice(), "MachineChoice didn't match!"),
                () -> assertEquals(USER, this.expectedRoundResult.getWinner(), "Game result didn't match!")
        );
    }

    @Test
    @DisplayName("Testing RoundResult equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualRoundResult = RoundResult.builder()
                .machineChoice(ROCK)
                .winner(USER)
                .build();

        assertTrue(this.expectedRoundResult.equals(actualRoundResult) &&
                actualRoundResult.equals(this.expectedRoundResult));
        assertEquals(this.expectedRoundResult.hashCode(), actualRoundResult.hashCode());
    }
}
