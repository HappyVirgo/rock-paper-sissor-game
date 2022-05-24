package com.al.qdt.rps.qry.services;

import com.al.qdt.common.dto.GameDto;

import java.util.Collection;
import java.util.UUID;

public interface RpsServiceV1 {

    /**
     * Returns all games.
     *
     * @return collection of games
     */
    Collection<GameDto> all();

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
    Collection<GameDto> findByUsername(String username);
}
