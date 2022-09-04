package com.al.qdt.rps.cmd.domain.services;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.api.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.api.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.domain.services.base.RpsBaseService;
import com.al.qdt.rps.cmd.domain.mappers.GameProtoMapper;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.al.qdt.common.infrastructure.config.AsyncConfig.ASYNC_TASK_EXECUTOR;

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
    @Async(ASYNC_TASK_EXECUTOR)
    public CompletableFuture<GameResultDto> playAsync(GameDto gameDto) {
        log.info("SERVICE: Playing game asynchronously...");
        return CompletableFuture.supplyAsync(() -> play(gameDto))
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("An error occurred: {}", ex.getMessage());
                    } else {
                        log.info("The game has been played successfully...");
                    }
                });
    }

    @Override
    public void deleteById(UUID id) {
        log.info("SERVICE: Deleting game by id: {}.", id.toString());
        this.commandDispatcher.send(DeleteGameCommand.builder()
                .id(id)
                .build());
        super.updateDeleteGameMetrics();
    }

    @Override
    public CompletableFuture<Void> deleteByIdAsync(UUID id) {
        log.info("SERVICE: Deleting game by id asynchronously...");
        return CompletableFuture.runAsync(() -> deleteById(id))
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("An error occurred: {}", ex.getMessage());
                    } else {
                        log.info("The game has been deleted successfully...");
                    }
                });
    }
}
