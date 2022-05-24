package com.al.qdt.score.qry.handlers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.common.events.score.ScoresDeletedEvent;
import com.al.qdt.score.qry.exceptions.ScoreNotFoundException;
import com.al.qdt.score.qry.repositories.ScoreRepository;
import com.al.qdt.score.qry.services.mappers.ScoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.al.qdt.score.qry.exceptions.ScoreNotFoundException.SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScoreEventHandler implements EventHandler {
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;

    @Override
    public void on(ScoresAddedEvent event) {
        log.info("Handling score added event with id: {}", event.getId().toString());
        this.scoreRepository.save(this.scoreMapper.toEntity(event));
    }

    @Override
    @CacheEvict(value = "scores", key = "#event.id.toString()")
    public void on(ScoresDeletedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling score deleted event with id: {}", scoreId.toString());
        this.deleteById(scoreId);
    }

    @Override
    @CacheEvict(value = "scores", key = "#event.id.toString()")
    public void on(GameDeletedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling game deleted event with id: {}", scoreId.toString());
        this.deleteById(scoreId);
    }

    private void deleteById(UUID scoreId) {
        if (this.scoreRepository.existsById(scoreId)) {
            this.scoreRepository.deleteById(scoreId);
            return;
        }
        throw new ScoreNotFoundException(String.format(SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE, scoreId.toString()));
    }
}
