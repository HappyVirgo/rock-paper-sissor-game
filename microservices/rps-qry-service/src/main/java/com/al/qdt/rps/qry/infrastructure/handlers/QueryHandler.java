package com.al.qdt.rps.qry.infrastructure.handlers;

import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.rps.qry.api.queries.FindAllGamesQuery;
import com.al.qdt.rps.qry.api.queries.FindGameByIdQuery;
import com.al.qdt.rps.qry.api.queries.FindGamesByUsernameQuery;

import java.util.List;

public interface QueryHandler {
    List<AbstractEntity> handle(FindAllGamesQuery query);

    List<AbstractEntity> handle(FindGameByIdQuery query);

    List<AbstractEntity> handle(FindGamesByUsernameQuery query);
}
