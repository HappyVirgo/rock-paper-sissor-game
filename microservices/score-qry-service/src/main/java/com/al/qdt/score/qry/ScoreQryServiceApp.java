package com.al.qdt.score.qry;

import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.score.qry.handlers.QueryHandler;
import com.al.qdt.score.qry.queries.FindAllScoresQuery;
import com.al.qdt.score.qry.queries.FindScoreByIdQuery;
import com.al.qdt.score.qry.queries.FindScoresByWinnerQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class ScoreQryServiceApp {
    private final QueryDispatcher queryDispatcher;

    private final QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(ScoreQryServiceApp.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        this.queryDispatcher.registerHandler(FindAllScoresQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindScoreByIdQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindScoresByWinnerQuery.class, this.queryHandler::handle);
    }
}
