package com.al.qdt.cqrs.queries;

import com.al.qdt.cqrs.domain.AbstractEntity;

import java.util.List;

public interface QueryHandlerMethod<T extends BaseQuery> {
    List<AbstractEntity> handle(T query);
}
