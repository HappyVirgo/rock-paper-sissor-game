package com.al.qdt.rps.qry.services;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.rps.qry.domain.Game;
import com.al.qdt.rps.qry.queries.FindAllGamesQuery;
import com.al.qdt.rps.qry.queries.FindGameByIdQuery;
import com.al.qdt.rps.qry.queries.FindGamesByUsernameQuery;
import com.al.qdt.rps.qry.services.mappers.GameDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.al.qdt.rps.qry.config.CacheConfig.GAMES_CACHE_NAME;
import static com.al.qdt.rps.qry.config.CacheConfig.GAME_CACHE_NAME;
import static com.al.qdt.rps.qry.config.CacheConfig.GAME_CACHE_NAMES;
import static com.al.qdt.rps.qry.config.CacheConfig.USERNAME_CACHE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = GAME_CACHE_NAMES)
public class RpsServiceV1Impl implements RpsServiceV1 {
    private final QueryDispatcher queryDispatcher;
    private final GameDtoMapper gameDtoMapper;

    @Override
    @Cacheable(cacheNames = GAMES_CACHE_NAME, sync = true)
    public Iterable<GameDto> all() {
        log.info("SERVICE: Getting all games...");
        return this.toListOfGameDto(this.queryDispatcher.send(new FindAllGamesQuery()));
    }

    @Override
    @Cacheable(cacheNames = GAME_CACHE_NAME, key = "#id.toString()", sync = true)
    public GameDto findById(UUID id) {
        log.info("SERVICE: Finding game by id: {}.", id.toString());
        final List<Game> games = this.queryDispatcher.send(new FindGameByIdQuery(id));
        return this.gameDtoMapper.toDto(games.get(0));
    }

    @Override
    @Cacheable(cacheNames = USERNAME_CACHE_NAME, key = "#username", sync = true)
    public Iterable<GameDto> findByUsername(String username) {
        log.info("SERVICE: Finding game by username: {}.", username);
        return this.toListOfGameDto(this.queryDispatcher.send(new FindGamesByUsernameQuery(username)));
    }

    /**
     * Converts game entities to dto objects.
     *
     * @param games games
     * @return collection of game dto objects
     */
    private Iterable<GameDto> toListOfGameDto(Iterable<Game> games) {
        final List<GameDto> gameDtoList = new ArrayList<>();
        games.forEach(game -> gameDtoList.add(this.gameDtoMapper.toDto(game)));
        return gameDtoList;
    }
}
