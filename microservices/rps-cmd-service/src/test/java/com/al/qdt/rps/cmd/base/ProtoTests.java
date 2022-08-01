package com.al.qdt.rps.cmd.base;

import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;
import com.al.qdt.rps.grpc.v1.common.Hand;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;
import com.al.qdt.rps.grpc.v1.services.GameRequest;
import com.google.protobuf.Message;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

import static com.al.qdt.common.helpers.Constants.SUCCESS_MESSAGE;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.rps.grpc.v1.common.Hand.ROCK;
import static com.al.qdt.rps.grpc.v1.common.Hand.SCISSORS;
import static com.al.qdt.rps.grpc.v1.common.Player.USER;

public interface ProtoTests {

    /**
     * Creates test proto3 message for base response.
     *
     * @return proto3 message
     */
    default BaseResponseDto createBaseResponseDto() {
        return BaseResponseDto.newBuilder()
                .setMessage(SUCCESS_MESSAGE)
                .build();
    }

    /**
     * Creates test proto3 message for game result.
     *
     * @return proto3 message
     */
    default GameResultDto createGameResultDto() {
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
    default GameDto createGameDto() {
        return GameDto.newBuilder()
                .setUsername(USERNAME_ONE)
                .setHand(ROCK)
                .build();
    }

    /**
     * Creates test proto3 message for a game.
     *
     * @return proto3 message
     */
    default GameDto createGameDto(Hand hand) {
        return GameDto.newBuilder()
                .setUsername(USERNAME_ONE)
                .setHand(hand)
                .build();
    }

    /**
     * Creates test proto3 message for a game request.
     *
     * @return proto3 message
     */
    default GameRequest createGameRequest(Hand hand) {
        return GameRequest.newBuilder()
                .setGame(createGameDto(hand))
                .build();
    }
}
