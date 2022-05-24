package com.al.qdt.common.events.rps;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.cqrs.events.BaseEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Jacksonized
@EqualsAndHashCode
public class GamePlayedEvent extends BaseEvent {

    @NotBlank
    @Min(value = 3, message = "Must be at least 3 characters long")
    String username;

    @EqualsAndHashCode.Exclude
    @NotNull
    Hand hand;

    @Builder
    public GamePlayedEvent(@JsonProperty("id") UUID id, @JsonProperty("username") String username, @JsonProperty("hand") Hand hand) {
        super(id);
        this.username = username;
        this.hand = hand;
    }
}
