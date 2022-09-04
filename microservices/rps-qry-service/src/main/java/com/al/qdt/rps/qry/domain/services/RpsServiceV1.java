package com.al.qdt.rps.qry.domain.services;

import com.al.qdt.common.api.dto.GameDto;

import java.util.UUID;

public interface RpsServiceV1 {

    /**
     * Returns all games.
     *
     * @return collection of games
     */
    Iterable<GameDto> all();

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
    Iterable<GameDto> findByUsername(String username);
}
