package com.al.qdt.cqrs.infrastructure;

import com.al.qdt.cqrs.events.BaseEvent;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, String aggregateType, Iterable<BaseEvent> events);

    List<BaseEvent> findByAggregateId(UUID aggregateId);

    List<UUID> findAllAggregateIds();
}
