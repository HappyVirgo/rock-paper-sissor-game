package com.al.qdt.rps.qry.handlers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.rps.qry.repositories.GameRepository;
import com.al.qdt.rps.qry.services.mappers.GameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import static com.al.qdt.rps.qry.config.CacheConfig.GAMES_CACHE_NAME;
import static com.al.qdt.rps.qry.config.CacheConfig.GAMES_PROTO_CACHE_NAME;
import static com.al.qdt.rps.qry.config.CacheConfig.GAME_CACHE_NAME;
import static com.al.qdt.rps.qry.config.CacheConfig.GAME_CACHE_NAMES;
import static com.al.qdt.rps.qry.config.CacheConfig.GAME_PROTO_CACHE_NAME;
import static com.al.qdt.rps.qry.exceptions.GameNotFoundException.GAME_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = GAME_CACHE_NAMES)
public class RpsEventHandler implements EventHandler {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = GAMES_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = GAMES_PROTO_CACHE_NAME, allEntries = true)})
    public void on(@Valid GamePlayedEvent event) {
        final var gameId = event.getId();
        log.info("Handling game played event with id: {}", gameId);
        if (!this.gameRepository.existsById(gameId)) {
            this.gameRepository.save(this.gameMapper.toEntity(event));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = GAME_CACHE_NAME, key = "#event.id.toString()"),
            @CacheEvict(cacheNames = GAME_PROTO_CACHE_NAME, key = "#event.id.toString()"),
            @CacheEvict(cacheNames = GAMES_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = GAMES_PROTO_CACHE_NAME, allEntries = true)})
    public void on(@Valid GameDeletedEvent event) {
        final var gameId = event.getId();
        log.info("Handling game deleted event with id: {}", gameId);
        this.gameRepository.deleteById(gameId);
    }
}
