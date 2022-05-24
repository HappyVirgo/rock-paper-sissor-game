package com.al.qdt.rps.cmd.services;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.dto.GameResponseDto;
import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.services.base.RpsBaseService;
import com.al.qdt.rps.cmd.services.mappers.GameDtoMapper;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RpsServiceV1Impl extends RpsBaseService implements RpsServiceV1 {
    private final CommandDispatcher commandDispatcher;
    private final GameDtoMapper gameDtoMapper;

    public RpsServiceV1Impl(GameService gameService, MeterRegistry meterRegistry, CommandDispatcher commandDispatcher, GameDtoMapper gameDtoMapper) {
        super(gameService, meterRegistry);
        this.commandDispatcher = commandDispatcher;
        this.gameDtoMapper = gameDtoMapper;
    }

    @Override
    public GameResponseDto play(GameDto gameDto) {
        final var id = UUID.randomUUID();
        log.info("SERVICE: Playing game with id: {}", id);
        final var command = this.gameDtoMapper.fromDto(gameDto);
        command.setId(id);
        final var roundResult = this.play(command.getHand());
        this.commandDispatcher.send(command);
        final var winner = roundResult.getWinner();
        final var addScoreCommand = AddScoreCommand.builder()
                .winner(winner)
                .build();
        addScoreCommand.setId(id);
        this.commandDispatcher.send(addScoreCommand);
        super.updatePlayedGameMetrics();
        return GameResponseDto.builder()
                .userChoice(gameDto.getHand())
                .machineChoice(roundResult.getMachineChoice().name())
                .result(roundResult.getWinner())
                .build();
    }

    @Override
    public void deleteById(UUID id) {
        log.info("SERVICE: Deleting game by id: {}.", id.toString());
        this.commandDispatcher.send(DeleteGameCommand.builder()
                .id(id)
                .build());
        super.updateDeleteGameMetrics();
    }
}