package com.al.qdt.rps.cmd.domain.aggregates;

import com.al.qdt.common.enums.Player;
import com.al.qdt.rps.cmd.base.CommandTests;
import com.al.qdt.rps.cmd.api.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.api.exceptions.GameException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.rps.cmd.domain.aggregates.RpsAggregate.SCORES_CANNOT_BE_ADDED_EXCEPTION_MESSAGE;
import static com.al.qdt.rps.cmd.domain.aggregates.RpsAggregate.WINNER_NULL_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing RpsAggregate class")
@Tag("aggregate")
class RpsAggregateTest implements CommandTests {

    PlayGameCommand playGameCommand;
    RpsAggregate rpsAggregate;

    @BeforeEach
    void setUp() {
        this.playGameCommand = createPlayGameCommand(TEST_UUID, USERNAME_ONE, ROCK);
        this.rpsAggregate = new RpsAggregate(this.playGameCommand);
    }

    @AfterEach
    void tearDown() {
        this.playGameCommand = null;
        this.rpsAggregate = null;
    }

    @Test
    @DisplayName("Testing apply method with PlayGameCommand class instance")
    void applyPlayGameCommandTest() {
        assertEquals(this.playGameCommand.getId(), this.rpsAggregate.getId());
        assertTrue(this.rpsAggregate.isPlayed());
    }

    @ParameterizedTest
    @EnumSource(Player.class)
    @DisplayName("Testing addScore() method with right parameter")
    void addScoreTest(Player winner) {
        assertDoesNotThrow(() -> this.rpsAggregate.addScore(winner));
        assertEquals(this.playGameCommand.getId(), this.rpsAggregate.getId());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing addScore() method with wrong parameter, throws GameException exception")
    void addScoreWithNullParamTest(Player winner) {
        final var actualException = assertThrows(Exception.class, () -> this.rpsAggregate.addScore(winner));

        assertThat(actualException).isInstanceOf(GameException.class);
        assertEquals(WINNER_NULL_EXCEPTION_MESSAGE, actualException.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing addScore() method without playing round first, throws GameException exception")
    void addScoreWithoutPlayingRound(Player winner) {
        this.rpsAggregate = new RpsAggregate();

        assertFalse(this.rpsAggregate.isPlayed());

        final var actualException = assertThrows(Exception.class, () -> this.rpsAggregate.addScore(winner));

        assertThat(actualException).isInstanceOf(GameException.class);
        assertEquals(SCORES_CANNOT_BE_ADDED_EXCEPTION_MESSAGE, actualException.getMessage());
    }

    @Test
    @DisplayName("Testing deleteGame() method with right parameter")
    void deleteGameTest() {
        assertDoesNotThrow(() -> this.rpsAggregate.deleteGame());
        assertEquals(this.playGameCommand.getId(), this.rpsAggregate.getId());
    }
}
