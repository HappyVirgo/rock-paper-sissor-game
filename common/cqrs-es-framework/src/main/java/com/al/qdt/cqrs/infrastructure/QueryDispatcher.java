package com.al.qdt.cqrs.infrastructure;

import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.cqrs.queries.BaseQuery;
import com.al.qdt.cqrs.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

    <T extends BaseQuery, U extends AbstractEntity> List<U> send(T query);
}
