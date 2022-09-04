package com.al.qdt.common.infrastructure.mongo.domain;

import com.al.qdt.common.domain.base.BaseDocument;
import com.al.qdt.cqrs.events.BaseEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@Document(collection = "events")
@TypeAlias("event")
public class Event extends BaseDocument {

    @Indexed(unique = true)
    @Field("aggregate_id")
    private UUID aggregateId;

    @Field("aggregate_type")
    private String aggregateType;

    @Field("event_type")
    private String eventType;

    @Field("event_data")
    private BaseEvent eventData;

    private ZonedDateTime played;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return getCreated() == null;
    }
}
