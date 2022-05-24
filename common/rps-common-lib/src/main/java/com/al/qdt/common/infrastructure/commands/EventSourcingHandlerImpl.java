package com.al.qdt.common.infrastructure.commands;

import com.al.qdt.cqrs.domain.AggregateRoot;
import com.al.qdt.cqrs.handlers.EventSourcingHandler;
import com.al.qdt.cqrs.infrastructure.EventStore;
import com.al.qdt.cqrs.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSourcingHandlerImpl<T extends AggregateRoot> implements EventSourcingHandler<T> {
    private final EventStore eventStore;
    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate, Class<T> clazz) {
        this.eventStore.saveEvents(aggregate.getId(), clazz.getTypeName(), aggregate.getUncommittedChanges());
        aggregate.markChangesAsCommitted();
    }

    @SneakyThrows({InstantiationException.class, IllegalAccessException.class,
            InvocationTargetException.class, NoSuchMethodException.class})
    @Override
    public T findById(UUID id, Class<T> clazz) {
        final var aggregate = clazz.getDeclaredConstructor().newInstance();
        final var events = this.eventStore.findByAggregateId(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
        }
        return aggregate;
    }

    @Override
    public void republishEvents(Class<T> clazz) {
        this.eventStore.findAllAggregateIds().forEach(aggregateId -> {
            final var aggregate = this.findById(aggregateId, clazz);
            if (aggregate == null) return;
            final var events = this.eventStore.findByAggregateId(aggregateId);
            events.forEach(event -> this.eventProducer.produce(event.getClass().getSimpleName(), event.getId().toString(), event));
        });
    }
}
