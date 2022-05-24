package com.al.qdt.rps.qry.handlers;

import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.rps.qry.queries.FindAllGamesQuery;
import com.al.qdt.rps.qry.queries.FindGamesByUsernameQuery;
import com.al.qdt.rps.qry.queries.FindGameByIdQuery;

import java.util.List;

public interface QueryHandler {
    List<AbstractEntity> handle(FindAllGamesQuery query);

    List<AbstractEntity> handle(FindGameByIdQuery query);

    List<AbstractEntity> handle(FindGamesByUsernameQuery query);
}
