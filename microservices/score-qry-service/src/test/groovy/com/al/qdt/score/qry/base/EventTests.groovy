package com.al.qdt.score.qry.base

import com.al.qdt.common.events.score.ScoresAddedEvent

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID

trait EventTests {

    /**
     * Creates test instance for scores added event.
     *
     * @return scores added event
     */
    ScoresAddedEvent createScoresAddedEvent() {
        ScoresAddedEvent.builder()
                .id(TEST_UUID)
                .winner(USER)
                .build()
    }
}
