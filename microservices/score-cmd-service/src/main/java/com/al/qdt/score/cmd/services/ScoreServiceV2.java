package com.al.qdt.score.cmd.services;

import java.util.UUID;

public interface ScoreServiceV2 {

    /**
     * Deletes scores by id.
     *
     * @param id score id,
     */
    void deleteById(UUID id);
}
