package com.al.qdt.rps.qry.handlers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.rps.qry.exceptions.GameNotFoundException;
import com.al.qdt.rps.qry.repositories.GameRepository;
import com.al.qdt.rps.qry.services.mappers.GameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.al.qdt.rps.qry.exceptions.GameNotFoundException.GAME_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RpsEventHandler implements EventHandler {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public void on(GamePlayedEvent event) {
        log.info("Handling game played event with id: {}", event.getId());
        this.gameRepository.save(this.gameMapper.toEntity(event));
    }

    @Override
    @CacheEvict(value = "games", key = "#event.id.toString()")
    public void on(GameDeletedEvent event) {
        final var gameId = event.getId();
        log.info("Handling game deleted event with id: {}", gameId.toString());
        if (this.gameRepository.existsById(gameId)) {
            this.gameRepository.deleteById(gameId);
            return;
        }
        throw new GameNotFoundException(String.format(GAME_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE, gameId.toString()));
    }
}
