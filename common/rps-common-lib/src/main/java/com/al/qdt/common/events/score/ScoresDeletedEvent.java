package com.al.qdt.common.events.score;

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
public class ScoresDeletedEvent extends BaseEvent {

    @Builder
    public ScoresDeletedEvent(@JsonProperty("id") UUID id) {
        super(id);
    }
}
