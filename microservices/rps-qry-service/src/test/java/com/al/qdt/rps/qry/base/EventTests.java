package com.al.qdt.rps.qry.base;

import com.al.qdt.common.events.rps.GamePlayedEvent;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;

public interface EventTests {

    /**
     * Creates test instance for game played event.
     *
     * @return game played event
     */
    default GamePlayedEvent createGamePlayedEvent() {
        final var gamePlayedEvent = GamePlayedEvent.builder()
                .username(USERNAME_ONE)
                .hand(ROCK)
                .build();
        gamePlayedEvent.setId(TEST_UUID);
        return gamePlayedEvent;
    }
}
