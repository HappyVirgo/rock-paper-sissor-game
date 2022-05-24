package com.al.qdt.rps.qry.base;

import com.al.qdt.rps.grpc.v1.dto.GameDto;

import static com.al.qdt.rps.grpc.v1.common.Hand.ROCK;

public interface ProtoTests {

    /**
     * Creates test proto3 message for a game.
     *
     * @param username username
     * @return proto3 message
     */
    default GameDto createGameDto(String username) {
        return GameDto.newBuilder()
                .setUsername(username)
                .setHand(ROCK)
                .build();
    }
}
