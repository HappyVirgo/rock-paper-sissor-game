package com.al.qdt.rps.cmd.aggregates;

import com.al.qdt.common.enums.Player;
import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.cqrs.domain.AggregateRoot;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.exceptions.GameException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RpsAggregate extends AggregateRoot {
    public static final String SCORES_CANNOT_BE_ADDED_EXCEPTION_MESSAGE = "Scores cannot be added before game!";
    public static final String WINNER_NULL_EXCEPTION_MESSAGE = "The winner cannot be null!";

    private boolean isPlayed;

    public RpsAggregate(PlayGameCommand command) {
        super.raiseEvent(GamePlayedEvent.builder()
                .id(command.getId())
                .username(command.getUsername())
                .hand(command.getHand())
                .build());
    }

    public void apply(GamePlayedEvent event) {
        this.id = event.getId();
        this.isPlayed = true;
    }

    public void addScore(Player winner) {
        if (!this.isPlayed) {
            throw new GameException(SCORES_CANNOT_BE_ADDED_EXCEPTION_MESSAGE);
        }
        if (winner == null) {
            throw new GameException(WINNER_NULL_EXCEPTION_MESSAGE);
        }
        raiseEvent(ScoresAddedEvent.builder()
                .id(this.id)
                .winner(winner)
                .build());
    }

    public void apply(ScoresAddedEvent event) {
        this.id = event.getId();
    }

    public void deleteGame() {
        raiseEvent(GameDeletedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(GameDeletedEvent event) {
        this.id = event.getId();
    }
}
