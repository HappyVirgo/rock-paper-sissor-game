package com.al.qdt.common.base;

import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;
import com.al.qdt.rps.grpc.v1.dto.ScoreDto;
import org.junit.jupiter.api.Tag;

import static com.al.qdt.common.helpers.Constants.*;
import static com.al.qdt.rps.grpc.v1.common.Hand.ROCK;
import static com.al.qdt.rps.grpc.v1.common.Hand.SCISSORS;
import static com.al.qdt.rps.grpc.v1.common.Player.USER;

@Tag(value = "proto")
public interface ProtoTests {

    /**
     * Creates test proto3 message for base response.
     *
     * @return proto3 message
     */
    default BaseResponseDto createBaseResponseProtoDto() {
        return BaseResponseDto.newBuilder()
                .setMessage(SUCCESS_MESSAGE)
                .build();
    }

    /**
     * Creates test proto3 message for game result.
     *
     * @return proto3 message
     */
    default GameResultDto createGameResultProtoDto() {
        return GameResultDto.newBuilder()
                .setUserChoice(ROCK.name())
                .setMachineChoice(SCISSORS.name())
                .setResult(USER.name())
                .build();
    }

    /**
     * Creates test proto3 message for a game.
     *
     * @return proto3 message
     */
    default GameDto createGameProtoDto() {
        return GameDto.newBuilder()
                .setId(TEST_ID)
                .setUsername(USERNAME_ONE)
                .setHand(ROCK)
                .build();
    }

    /**
     * Creates test proto3 message for a score.
     *
     * @return proto3 message
     */
    default ScoreDto createScoreProtoDto() {
        return ScoreDto.newBuilder()
                .setId(TEST_ID)
                .setWinner(USER.name())
                .build();
    }
}
