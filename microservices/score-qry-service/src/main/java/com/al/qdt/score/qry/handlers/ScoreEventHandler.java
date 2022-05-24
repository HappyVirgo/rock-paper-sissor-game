package com.al.qdt.score.qry.handlers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.common.events.score.ScoresDeletedEvent;
import com.al.qdt.score.qry.exceptions.ScoreNotFoundException;
import com.al.qdt.score.qry.repositories.ScoreRepository;
import com.al.qdt.score.qry.services.mappers.ScoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.UUID;

import static com.al.qdt.score.qry.exceptions.ScoreNotFoundException.SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "scoresCache")
public class ScoreEventHandler implements EventHandler {
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "scores", allEntries = true),
            @CacheEvict(cacheNames = "scoresProto", allEntries = true),
            @CacheEvict(cacheNames = "winners", allEntries = true),
            @CacheEvict(cacheNames = "winnersProto", allEntries = true)})
    public void on(@Valid ScoresAddedEvent event) {
        log.info("Handling score added event with id: {}", event.getId().toString());
        this.scoreRepository.save(this.scoreMapper.toEntity(event));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "score", key = "#event.id.toString()"),
            @CacheEvict(cacheNames = "scoreProto", key = "#event.id.toString()"),
            @CacheEvict(cacheNames = "scores", allEntries = true),
            @CacheEvict(cacheNames = "scoresProto", allEntries = true),
            @CacheEvict(cacheNames = "winners", allEntries = true),
            @CacheEvict(cacheNames = "winnersProto", allEntries = true)})
    public void on(@Valid ScoresDeletedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling score deleted event with id: {}", scoreId.toString());
        this.deleteById(scoreId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "score", key = "#event.id.toString()"),
            @CacheEvict(cacheNames = "scoreProto", key = "#event.id.toString()"),
            @CacheEvict(cacheNames = "scores", allEntries = true),
            @CacheEvict(cacheNames = "scoresProto", allEntries = true),
            @CacheEvict(cacheNames = "winners", allEntries = true),
            @CacheEvict(cacheNames = "winnersProto", allEntries = true)})
    public void on(@Valid GameDeletedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling game deleted event with id: {}", scoreId.toString());
        this.deleteById(scoreId);
    }

    /**
     * Delete score by id.
     *
     * @param scoreId score id
     */
    private void deleteById(UUID scoreId) {
        if (this.scoreRepository.existsById(scoreId)) {
            this.scoreRepository.deleteById(scoreId);
            return;
        }
        throw new ScoreNotFoundException(String.format(SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE, scoreId.toString()));
    }
}
