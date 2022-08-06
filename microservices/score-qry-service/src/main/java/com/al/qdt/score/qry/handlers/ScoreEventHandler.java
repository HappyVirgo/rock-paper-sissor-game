package com.al.qdt.score.qry.handlers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.common.events.score.ScoresDeletedEvent;
import com.al.qdt.score.qry.repositories.ScoreRepository;
import com.al.qdt.score.qry.services.mappers.ScoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.UUID;

import static com.al.qdt.score.qry.config.CacheConfig.SCORES_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.SCORES_PROTO_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.SCORE_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.SCORE_CACHE_NAMES;
import static com.al.qdt.score.qry.config.CacheConfig.SCORE_PROTO_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.WINNERS_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.WINNERS_PROTO_CACHE_NAME;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = SCORE_CACHE_NAMES)
public class ScoreEventHandler implements EventHandler {
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = SCORES_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = SCORES_PROTO_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = WINNERS_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = WINNERS_PROTO_CACHE_NAME, allEntries = true)})
    public void on(@Valid ScoresAddedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling score added event with id: {}", scoreId);
        if (!this.scoreRepository.existsById(scoreId)) {
            this.scoreRepository.save(this.scoreMapper.toEntity(event));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = SCORE_CACHE_NAME, key = "#event.id.toString()"),
            @CacheEvict(cacheNames = SCORE_PROTO_CACHE_NAME, key = "#event.id.toString()"),
            @CacheEvict(cacheNames = SCORES_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = SCORES_PROTO_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = WINNERS_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = WINNERS_PROTO_CACHE_NAME, allEntries = true)})
    public void on(@Valid ScoresDeletedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling score deleted event with id: {}", scoreId);
        this.scoreRepository.deleteById(scoreId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = SCORE_CACHE_NAME, key = "#event.id.toString()"),
            @CacheEvict(cacheNames = SCORE_PROTO_CACHE_NAME, key = "#event.id.toString()"),
            @CacheEvict(cacheNames = SCORES_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = SCORES_PROTO_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = WINNERS_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = WINNERS_PROTO_CACHE_NAME, allEntries = true)})
    public void on(@Valid GameDeletedEvent event) {
        final var scoreId = event.getId();
        log.info("Handling game deleted event with id: {}", scoreId);
        this.scoreRepository.deleteById(scoreId);
    }
}
