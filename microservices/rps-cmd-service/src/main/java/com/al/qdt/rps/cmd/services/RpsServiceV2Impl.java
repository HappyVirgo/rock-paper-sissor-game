package com.al.qdt.rps.cmd.services;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.services.base.RpsBaseService;
import com.al.qdt.rps.cmd.services.mappers.GameProtoMapper;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RpsServiceV2Impl extends RpsBaseService implements RpsServiceV2 {
    private final CommandDispatcher commandDispatcher;
    private final GameProtoMapper gameProtoMapper;

    public RpsServiceV2Impl(GameService gameService, MeterRegistry meterRegistry, CommandDispatcher commandDispatcher, GameProtoMapper gameProtoMapper) {
        super(gameService, meterRegistry);
        this.commandDispatcher = commandDispatcher;
        this.gameProtoMapper = gameProtoMapper;
    }

    @Override
    public GameResultDto play(GameDto gameDto) {
        final var id = UUID.randomUUID();
        log.info("SERVICE: Playing game with id: {}", id.toString());
        final var command = this.gameProtoMapper.fromDto(gameDto);
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
        return GameResultDto.newBuilder()
                .setUserChoice(gameDto.getHand().name())
                .setMachineChoice(roundResult.getMachineChoice().name())
                .setResult(winner.name())
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
