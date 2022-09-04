package com.al.qdt.score.qry.domain.services;

import com.al.qdt.common.api.dto.ScoreDto;
import com.al.qdt.common.enums.Player;

import java.util.UUID;

public interface ScoreServiceV1 {

    /**
     * Returns all scores.
     *
     * @return collection of scores
     */
    Iterable<ScoreDto> all();

    /**
     * Finds score by id.
     *
     * @param id score id
     * @return found score
     */
    ScoreDto findById(UUID id);

    /**
     * Finds scores by winner.
     *
     * @param player winner
     * @return found scores
     */
    Iterable<ScoreDto> findByWinner(Player player);
}
