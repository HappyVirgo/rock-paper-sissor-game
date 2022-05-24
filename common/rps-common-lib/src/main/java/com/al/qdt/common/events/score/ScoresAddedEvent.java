package com.al.qdt.common.events.score;

import com.al.qdt.common.enums.Player;
import com.al.qdt.cqrs.events.BaseEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Jacksonized
@EqualsAndHashCode
public class ScoresAddedEvent extends BaseEvent {

    @EqualsAndHashCode.Exclude
    @NotNull
    Player winner;

    @Builder
    public ScoresAddedEvent(@JsonProperty("id") UUID id, @JsonProperty("winner") Player winner) {
        super(id);
        this.winner = winner;
    }
}
