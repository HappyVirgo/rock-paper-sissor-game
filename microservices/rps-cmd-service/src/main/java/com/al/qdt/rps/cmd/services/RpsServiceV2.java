package com.al.qdt.rps.cmd.services;

import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
     * Plays game asynchronously.
     *
     * @param gameDto game round user inputs
     * @return game result
     */
    CompletableFuture<GameResultDto> playAsync(GameDto gameDto);

    /**
     * Deletes game by id.
     *
     * @param id game id
     */
    void deleteById(UUID id);

    /**
     * Deletes game by id asynchronously.
     *
     * @param id game id
     */
    CompletableFuture<Void> deleteByIdAsync(UUID id);
}
