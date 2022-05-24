package com.al.qdt.common.events.rps;

import com.al.qdt.cqrs.events.BaseEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Jacksonized
@EqualsAndHashCode
public class GameDeletedEvent extends BaseEvent {

    @Builder
    public GameDeletedEvent(@JsonProperty("id") UUID id) {
        super(id);
    }
}
