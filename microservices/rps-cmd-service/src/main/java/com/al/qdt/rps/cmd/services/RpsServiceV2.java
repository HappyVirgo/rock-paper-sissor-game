package com.al.qdt.rps.cmd.services;

import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;

import java.util.UUID;

/**
 * This interface describes RPS game service functionality.
 */
public interface RpsServiceV2 {

    /**
     * Plays game.
     *
     * @param gameDto game round user inputs
     * @return game result
     */
    GameResultDto play(GameDto gameDto);

    /**
     * Deletes game by id.
     *
     * @param id game id
     */
    void deleteById(UUID id);
}
