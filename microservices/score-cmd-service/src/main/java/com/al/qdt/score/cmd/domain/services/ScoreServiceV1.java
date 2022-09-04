package com.al.qdt.score.cmd.domain.services;

import java.util.UUID;

public interface ScoreServiceV1 {

    /**
     * Deletes scores by id.
     *
     * @param id score id,
     */
    void deleteById(UUID id);
}
