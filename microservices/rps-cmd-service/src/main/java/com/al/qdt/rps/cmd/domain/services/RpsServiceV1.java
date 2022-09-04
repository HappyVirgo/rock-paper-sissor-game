package com.al.qdt.rps.cmd.domain.services;

import com.al.qdt.common.api.dto.GameDto;
import com.al.qdt.common.api.dto.GameResponseDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
     * Plays game asynchronously.
     *
     * @param gameDto game round user inputs
     * @return game result
     */
    CompletableFuture<GameResponseDto> playAsync(GameDto gameDto);

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
