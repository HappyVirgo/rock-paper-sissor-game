package com.al.qdt.common.infrastructure.commands;

import com.al.qdt.common.mongo.domain.Event;
import com.al.qdt.common.mongo.repositories.EventRepository;
import com.al.qdt.cqrs.events.BaseEvent;
import com.al.qdt.cqrs.exceptions.AggregateException;
import com.al.qdt.cqrs.exceptions.AggregateNotFoundException;
import com.al.qdt.cqrs.exceptions.EventNotFoundException;
import com.al.qdt.cqrs.infrastructure.EventStore;
import com.al.qdt.cqrs.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventStoreImpl implements EventStore {
    private static final String EVENT_CREATING_EXCEPTION_MESSAGE = "Error occurred while creating an event!";
    private static final String EVENT_SAVING_EXCEPTION_MESSAGE = "Error occurred while saving an event to the repository!";
    private static final String EVENT_ID_NOT_VALID_EXCEPTION_MESSAGE = "Incorrect id provided!";
    private static final String EVENTS_NOT_FOUND_EXCEPTION_MESSAGE = "No Events Found!";

    private final EventProducer eventProducer;
    private final EventRepository eventRepository;

    /**
     * Save events to the data storage.
     *
     * @param aggregateId   aggregate id
     * @param aggregateType aggregate type
     * @param events        collection of events
     */
    @Override
    public void saveEvents(UUID aggregateId, String aggregateType, Iterable<BaseEvent> events) {
        events.forEach((event) -> {
            final var eventModel = Event.builder()
                    .aggregateId(aggregateId)
                    .aggregateType(aggregateType)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .played(ZonedDateTime.now())
                    .build();
            if (eventModel == null) {
                log.error(EVENT_CREATING_EXCEPTION_MESSAGE);
                throw new AggregateException(EVENT_CREATING_EXCEPTION_MESSAGE);
            }
            final var persistedEvent = this.eventRepository.save(eventModel);
            if (persistedEvent.getId() == null) {
                log.error(EVENT_SAVING_EXCEPTION_MESSAGE);
                throw new AggregateException(EVENT_SAVING_EXCEPTION_MESSAGE);
            }
            this.eventProducer.produce(event.getClass().getSimpleName(), event.getId().toString(), event);
        });
    }

    /**
     * Find events by aggregate id.
     *
     * @param aggregateId aggregate id
     * @return collection of events
     */
    @Override
    public List<BaseEvent> findByAggregateId(UUID aggregateId) {
        final var events = this.eventRepository.findByAggregateId(aggregateId);
        if (events == null || events.isEmpty()) {
            log.error(EVENT_ID_NOT_VALID_EXCEPTION_MESSAGE);
            throw new AggregateNotFoundException(EVENT_ID_NOT_VALID_EXCEPTION_MESSAGE);
        }
        return events.stream().map(Event::getEventData).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Find all aggregate ids.
     *
     * @return collection of aggregate ids
     */
    @Override
    public List<UUID> findAllAggregateIds() {
        final var events = this.eventRepository.findAll();
        if (events.isEmpty()) {
            log.error(EVENTS_NOT_FOUND_EXCEPTION_MESSAGE);
            throw new EventNotFoundException(EVENTS_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return events.stream().map(Event::getAggregateId).distinct().collect(Collectors.toUnmodifiableList());
    }
}
