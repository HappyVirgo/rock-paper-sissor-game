package com.al.qdt.rps.qry.handlers;

import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.rps.qry.exceptions.GameNotFoundException;
import com.al.qdt.rps.qry.queries.FindAllGamesQuery;
import com.al.qdt.rps.qry.queries.FindGameByIdQuery;
import com.al.qdt.rps.qry.queries.FindGamesByUsernameQuery;
import com.al.qdt.rps.qry.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.al.qdt.rps.qry.exceptions.GameNotFoundException.GAMES_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.al.qdt.rps.qry.exceptions.GameNotFoundException.GAME_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.al.qdt.rps.qry.exceptions.GameNotFoundException.GAME_BY_USERNAME_NOT_FOUND_EXCEPTION_MESSAGE;

@Slf4j
@Service
@Transactional(readOnly = true) // for performance optimization, avoids dirty checks on all retrieved entities
@RequiredArgsConstructor
public class RpsQueryHandler implements QueryHandler {
    private final GameRepository gameRepository;

    @Override
    public List<AbstractEntity> handle(FindAllGamesQuery query) {
        log.info("Handling find all games query.");
        final var games = this.gameRepository.findAll();
        if (games.isEmpty()) {
            throw new GameNotFoundException(GAMES_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return new ArrayList<>(games);
    }

    @Override
    public List<AbstractEntity> handle(FindGameByIdQuery query) {
        final var gameId = query.getId();
        log.info("Handling find game by id query for id: {}.", gameId.toString());
        final var game = this.gameRepository.findById(gameId);
        if (game.isEmpty()) {
            throw new GameNotFoundException(String.format(GAME_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE, gameId));
        }
        return List.of(game.get());
    }

    @Override
    public List<AbstractEntity> handle(FindGamesByUsernameQuery query) {
        final var username = query.getUsername();
        log.info("Handling find games by username query for username: {}.", username);
        final var games = this.gameRepository.findGameByUsername(username);
        if (games.isEmpty()) {
            throw new GameNotFoundException(String.format(GAME_BY_USERNAME_NOT_FOUND_EXCEPTION_MESSAGE, username));
        }
        return new ArrayList<>(games);
    }
}
