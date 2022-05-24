package com.al.qdt.score.qry.services;

import com.al.qdt.rps.grpc.v1.common.Player;
import com.al.qdt.rps.grpc.v1.dto.ScoreDto;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse;

import java.util.UUID;

public interface ScoreServiceV2 {

    /**
     * Returns all scores.
     *
     * @return collection of scores
     */
    ListOfScoresResponse all();

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
    ListOfScoresResponse findByWinner(Player player);
}
