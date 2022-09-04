package com.al.qdt.common.domain.base;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.common.events.score.ScoresDeletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;

@Tag(value = "event")
public interface EventTests {
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Creates test instance for game played event.
     *
     * @return game played event
     */
    default GamePlayedEvent createGamePlayedEvent() {
        return GamePlayedEvent.builder()
                .id(TEST_UUID)
                .username(USERNAME_ONE)
                .hand(ROCK)
                .build();
    }

    /**
     * Creates test instance for game deleted event.
     *
     * @return game deleted event
     */
    default GameDeletedEvent createGameDeletedEvent() {
        return GameDeletedEvent.builder()
                .id(TEST_UUID)
                .build();
    }

    /**
     * Creates test instance for scores added event.
     *
     * @return scores added event
     */
    default ScoresAddedEvent createScoresAddedEvent() {
        return ScoresAddedEvent.builder()
                .id(TEST_UUID)
                .winner(USER)
                .build();
    }

    /**
     * Creates test instance for scores deleted event.
     *
     * @return scores deleted event
     */
    default ScoresDeletedEvent createScoresDeletedEvent() {
        return ScoresDeletedEvent.builder()
                .id(TEST_UUID)
                .build();
    }
}
