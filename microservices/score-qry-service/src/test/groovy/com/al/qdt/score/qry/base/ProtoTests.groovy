package com.al.qdt.score.qry.base

import com.al.qdt.rps.grpc.v1.dto.ScoreDto
import static com.al.qdt.rps.grpc.v1.common.Player.USER

trait ProtoTests {

    /**
     * Creates test instance for score proto object.
     *
     * @return score proto object
     */
    ScoreDto createScoreDto() {
        ScoreDto.newBuilder()
                .setWinner(USER.name())
                .build()
    }
}
