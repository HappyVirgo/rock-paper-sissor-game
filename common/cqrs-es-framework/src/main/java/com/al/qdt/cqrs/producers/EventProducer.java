package com.al.qdt.cqrs.producers;

import com.al.qdt.cqrs.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, String key, BaseEvent event);
}
