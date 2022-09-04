package com.al.qdt.rps.qry.base;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.rps.qry.domain.entities.Game;

import java.util.UUID;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID_TWO;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.common.helpers.Constants.USERNAME_TWO;

public interface EntityTests {

    /**
     * Creates test instance for game entity object.
     *
     * @param id       game id
     * @param username username
     * @param hand     user choice
     * @return game entity object
     */
    default Game createGame(UUID id, String username, Hand hand) {
        final var game = Game.builder()
                .username(username)
                .hand(hand)
                .build();
        game.setId(id);
        return game;
    }

    /**
     * Creates test instance for game entity object.
     *
     * @return game entity object
     */
    default Game createSecondGame() {
        final var game = Game.builder()
                .username(USERNAME_TWO)
                .hand(ROCK)
                .build();
        game.setId(TEST_UUID_TWO);
        return game;
    }

    /**
     * Creates test instance for game entity object with null id.
     *
     * @return game entity object
     */
    default Game createGameWithNulUUID() {
        return Game.builder()
                .username(USERNAME_ONE)
                .hand(ROCK)
                .build();
    }
}
