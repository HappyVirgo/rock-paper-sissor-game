package com.al.qdt.score.cmd.services;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.score.cmd.commands.DeleteScoreCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreServiceV1Impl implements ScoreServiceV1 {
    private final CommandDispatcher commandDispatcher;

    @Override
    public void deleteById(UUID id) {
        log.info("SERVICE: Deleting scores by id: {}.", id.toString());
        this.commandDispatcher.send(DeleteScoreCommand.builder()
                .id(id)
                .build());
    }
}
