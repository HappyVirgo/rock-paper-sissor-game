package com.al.qdt.rps.qry.infrastructure.handlers;

import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;

public interface EventHandler {
    void on(GamePlayedEvent event);

    void on(GameDeletedEvent event);
}
