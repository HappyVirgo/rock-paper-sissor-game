package com.al.qdt.cqrs.domain;

import com.al.qdt.cqrs.events.BaseEvent;
import com.al.qdt.cqrs.exceptions.AggregateException;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {
    private static final String APPLY_METHOD_NOT_FOUND_EXCEPTION_MESSAGE = "The apply method was not found in the aggregate for {0}";
    private static final String APPLY_METHOD_EXECUTION_EXCEPTION_MESSAGE = "Error applying event to aggregate: ";
    private static final String APPLY_METHOD_NAME = "apply";

    @Getter
    protected UUID id;

    @Getter
    private final List<BaseEvent> uncommittedChanges = new ArrayList<>();

    public void markChangesAsCommitted() {
        this.uncommittedChanges.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent) {
        try {
            final var method = this.getClass().getDeclaredMethod(APPLY_METHOD_NAME, event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            throw new AggregateException(MessageFormat.format(APPLY_METHOD_NOT_FOUND_EXCEPTION_MESSAGE, event.getClass().getName()));
        } catch (Exception e) {
            throw new AggregateException(APPLY_METHOD_EXECUTION_EXCEPTION_MESSAGE);
        } finally {
            if (isNewEvent) {
                this.uncommittedChanges.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
