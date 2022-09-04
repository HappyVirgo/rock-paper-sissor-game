package com.al.qdt.score.cmd.infrastructure.handlers;

import com.al.qdt.cqrs.handlers.EventSourcingHandler;
import com.al.qdt.score.cmd.domain.aggregates.ScoreAggregate;
import com.al.qdt.score.cmd.api.commands.DeleteScoreCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreCommandHandler implements CommandHandler {
    private final EventSourcingHandler<ScoreAggregate> eventSourcingHandler;

    @Override
    public void handle(DeleteScoreCommand command) {
        this.eventSourcingHandler.save(new ScoreAggregate(command), ScoreAggregate.class);
    }
}
