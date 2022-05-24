package com.al.qdt.score.qry.handlers;

import com.al.qdt.common.enums.Player;
import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.score.qry.exceptions.ScoreNotFoundException;
import com.al.qdt.score.qry.queries.FindAllScoresQuery;
import com.al.qdt.score.qry.queries.FindScoreByIdQuery;
import com.al.qdt.score.qry.queries.FindScoresByWinnerQuery;
import com.al.qdt.score.qry.repositories.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.al.qdt.score.qry.exceptions.ScoreNotFoundException.SCORES_BY_WINNER_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.al.qdt.score.qry.exceptions.ScoreNotFoundException.SCORES_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.al.qdt.score.qry.exceptions.ScoreNotFoundException.SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE;

@Slf4j
@Service
@Transactional(readOnly = true) // for performance optimization, avoids dirty checks on all retrieved entities
@RequiredArgsConstructor
public class ScoreQueryHandler implements QueryHandler {
    private final ScoreRepository scoreRepository;

    @Override
    public List<AbstractEntity> handle(FindAllScoresQuery query) {
        log.info("Handling find all scores query.");
        final var scores = this.scoreRepository.findAll();
        if (scores.isEmpty()) {
            throw new ScoreNotFoundException(SCORES_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return new ArrayList<>(scores);
    }

    @Override
    public List<AbstractEntity> handle(FindScoreByIdQuery query) {
        final var scoreId = query.getId();
        log.info("Handling find score by id query for id: {}", scoreId.toString());
        final var score = this.scoreRepository.findById(scoreId);
        if (score.isEmpty()) {
            throw new ScoreNotFoundException(String.format(SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE, scoreId));
        }
        return List.of(score.get());
    }

    @Override
    public List<AbstractEntity> handle(FindScoresByWinnerQuery query) {
        final var winner = query.getWinner();
        log.info("Handling find scores by winner query for winner: {}", winner);
        final var scores = this.scoreRepository.findByWinner(Player.valueOf(winner));
        if (scores.isEmpty()) {
            throw new ScoreNotFoundException(String.format(SCORES_BY_WINNER_NOT_FOUND_EXCEPTION_MESSAGE, winner));
        }
        return new ArrayList<>(scores);
    }
}
