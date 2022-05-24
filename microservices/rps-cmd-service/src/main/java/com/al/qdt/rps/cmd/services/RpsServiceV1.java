package com.al.qdt.rps.cmd.services;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.dto.GameResponseDto;

import java.util.UUID;

/**
 * This interface describes PPS game service functionality.
 */
public interface RpsServiceV1 {

    /**
     * Plays game.
     *
     * @param gameDto game round user inputs
     * @return game result
     */
    GameResponseDto play(GameDto gameDto);

    /**
     * Deletes game by id.
     *
     * @param id game id
     */
    void deleteById(UUID id);
}
