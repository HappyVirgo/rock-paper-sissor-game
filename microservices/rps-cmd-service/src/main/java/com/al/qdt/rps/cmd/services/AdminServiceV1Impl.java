package com.al.qdt.rps.cmd.services;

import com.al.qdt.common.dto.BaseResponseDto;
import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.al.qdt.common.config.AsyncConfig.ASYNC_TASK_EXECUTOR;
import static com.al.qdt.common.helpers.Constants.SUCCESS_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceV1Impl implements AdminServiceV1 {
    private final CommandDispatcher commandDispatcher;

    @Override
    public BaseResponseDto restoreDb() {
        this.commandDispatcher.send(RestoreDbCommand.builder()
                .id(UUID.randomUUID())
                .build());
        return BaseResponseDto.builder()
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    @Async(ASYNC_TASK_EXECUTOR)
    public CompletableFuture<BaseResponseDto> restoreDbAsync() {
        log.info("SERVICE: Restoring the database asynchronously...");
        return CompletableFuture.supplyAsync(this::restoreDb)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("An error occurred: {}", ex.getMessage());
                    } else {
                        log.info("The database has been restored successfully...");
                    }
                });
    }
}
