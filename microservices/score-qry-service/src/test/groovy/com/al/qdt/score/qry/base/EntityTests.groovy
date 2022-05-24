package com.al.qdt.score.qry.base

import com.al.qdt.common.enums.Player
import com.al.qdt.score.qry.domain.Score

import static com.al.qdt.common.helpers.Constants.TEST_UUID_TWO
import static com.al.qdt.common.enums.Player.USER

trait EntityTests {

    /**
     * Creates test instance for score entity object.
     *
     * @param id game id
     * @param winner winner of the game
     * @return score entity object
     */
    Score createScore(UUID id, Player winner) {
        new Score(id: id, winner: winner)
    }

    /**
     * Creates test instance for score entity object.
     *
     * @return score entity object
     */
    Score createSecondScore() {
        new Score(id: TEST_UUID_TWO, winner: USER)
    }

    /**
     * Creates test instance with nullable id for score entity object.
     *
     * @return score entity object
     */
    Score createScoreWithNulUUID() {
        new Score(id: null, winner: USER)
    }
}
