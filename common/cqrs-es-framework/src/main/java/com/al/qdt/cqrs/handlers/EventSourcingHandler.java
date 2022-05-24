package com.al.qdt.cqrs.handlers;

import com.al.qdt.cqrs.domain.AggregateRoot;

import java.util.UUID;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate, Class<T> clazz);

    T findById(UUID id, Class<T> clazz);

    void republishEvents(Class<T> clazz);
}
