package com.al.qdt.score.qry.services;

import com.al.qdt.common.dto.ScoreDto;
import com.al.qdt.common.enums.Player;

import java.util.Collection;
import java.util.UUID;

public interface ScoreServiceV1 {

    /**
     * Returns all scores.
     *
     * @return collection of scores
     */
    Collection<ScoreDto> all();

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
    Collection<ScoreDto> findByWinner(Player player);
}
