package com.al.qdt.cqrs.events;

import com.al.qdt.cqrs.messages.Message;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public abstract class BaseEvent extends Message {
    protected BaseEvent(UUID id) {
        super(id);
    }
}
