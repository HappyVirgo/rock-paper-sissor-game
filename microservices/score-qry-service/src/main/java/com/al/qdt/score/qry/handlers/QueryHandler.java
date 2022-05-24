package com.al.qdt.score.qry.handlers;

import com.al.qdt.cqrs.domain.AbstractEntity;
import com.al.qdt.score.qry.queries.FindAllScoresQuery;
import com.al.qdt.score.qry.queries.FindScoresByWinnerQuery;
import com.al.qdt.score.qry.queries.FindScoreByIdQuery;

import java.util.List;

public interface QueryHandler {
    List<AbstractEntity> handle(FindAllScoresQuery query);

    List<AbstractEntity> handle(FindScoreByIdQuery query);

    List<AbstractEntity> handle(FindScoresByWinnerQuery query);
}
