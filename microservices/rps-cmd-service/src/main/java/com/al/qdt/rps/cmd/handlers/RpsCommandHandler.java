package com.al.qdt.rps.cmd.handlers;

import com.al.qdt.cqrs.handlers.EventSourcingHandler;
import com.al.qdt.rps.cmd.aggregates.RpsAggregate;
import com.al.qdt.rps.cmd.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RpsCommandHandler implements CommandHandler {
    private final EventSourcingHandler<RpsAggregate> eventSourcingHandler;

    @Override
    public void handle(PlayGameCommand command) {
        this.eventSourcingHandler.save(new RpsAggregate(command), RpsAggregate.class);
    }

    @Override
    public void handle(AddScoreCommand command) {
        final var aggregate = this.eventSourcingHandler.findById(command.getId(), RpsAggregate.class);
        aggregate.addScore(command.getWinner());
        this.eventSourcingHandler.save(aggregate, RpsAggregate.class);
    }

    @Override
    public void handle(DeleteGameCommand command) {
        final var aggregate = this.eventSourcingHandler.findById(command.getId(), RpsAggregate.class);
        aggregate.deleteGame();
        this.eventSourcingHandler.save(aggregate, RpsAggregate.class);
    }

    @Override
    public void handle(RestoreDbCommand command) {
        this.eventSourcingHandler.republishEvents(RpsAggregate.class);
    }
}
