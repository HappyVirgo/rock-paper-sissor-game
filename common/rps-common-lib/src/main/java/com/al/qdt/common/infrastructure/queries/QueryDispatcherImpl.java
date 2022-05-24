package com.al.qdt.common.infrastructure.queries;

import com.al.qdt.common.exceptions.DispatcherException;
import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.cqrs.queries.BaseQuery;
import com.al.qdt.cqrs.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QueryDispatcherImpl implements QueryDispatcher {
    private static final String NO_COMMAND_HANDLER_REGISTERED_EXCEPTION_MESSAGE = "No command handler was registered!";
    private static final String SEND_COMMAND_EXCEPTION_MESSAGE = "Cannot send command to more than one handler!";

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        final var handlers = this.routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <T extends BaseQuery, U extends AbstractEntity> List<U> send(T query) {
        final var handlers = this.routes.get(query.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new DispatcherException(NO_COMMAND_HANDLER_REGISTERED_EXCEPTION_MESSAGE);
        }
        if (handlers.size() > 1) {
            throw new DispatcherException(SEND_COMMAND_EXCEPTION_MESSAGE);
        }
        return handlers.get(0).handle(query);
    }
}
