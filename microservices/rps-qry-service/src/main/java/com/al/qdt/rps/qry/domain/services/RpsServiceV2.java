package com.al.qdt.rps.qry.domain.services;

import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.services.ListOfGamesResponse;
import com.google.protobuf.StringValue;

import java.util.UUID;

public interface RpsServiceV2 {

    /**
     * Returns all games.
     *
     * @return collection of games
     */
    ListOfGamesResponse all();

    /**
     * Finds game by id.
     *
     * @param id game id
     * @return found game
     */
    GameDto findById(UUID id);

    /**
     * Finds games by username.
     *
     * @param username username
     * @return found games
     */
    ListOfGamesResponse findByUsername(StringValue username);
}
