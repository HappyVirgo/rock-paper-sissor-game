package com.al.qdt.score.cmd.aggregates;

import com.al.qdt.score.cmd.commands.DeleteScoreCommand;
import com.al.qdt.common.events.score.ScoresDeletedEvent;
import com.al.qdt.cqrs.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScoreAggregate extends AggregateRoot {

    public ScoreAggregate(DeleteScoreCommand command) {
        super.raiseEvent(ScoresDeletedEvent.builder()
                .id(command.getId())
                .build());
    }

    public void apply(ScoresDeletedEvent event) {
        this.id = event.getId();
    }
}
