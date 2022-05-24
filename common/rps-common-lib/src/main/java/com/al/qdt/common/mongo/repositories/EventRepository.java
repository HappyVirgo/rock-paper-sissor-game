package com.al.qdt.common.mongo.repositories;

import com.al.qdt.common.mongo.domain.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * MongoDB repository to CRUD an application events.
 */
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByAggregateId(UUID aggregateId);
}
