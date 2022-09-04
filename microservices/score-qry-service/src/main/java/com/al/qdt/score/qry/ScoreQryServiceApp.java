package com.al.qdt.score.qry;

import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.score.qry.api.queries.FindAllScoresQuery;
import com.al.qdt.score.qry.api.queries.FindScoreByIdQuery;
import com.al.qdt.score.qry.api.queries.FindScoresByWinnerQuery;
import com.al.qdt.score.qry.infrastructure.handlers.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ScoreQryServiceApp {
    private final QueryDispatcher queryDispatcher;

    private final QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(ScoreQryServiceApp.class, args);
    }

    /**
     * Registering queries to appropriate handlers.
     */
    @PostConstruct
    public void registerHandlers() {
        // Queries. These queries return a result and do not change the state of the system, and they are free of side effects.
        this.queryDispatcher.registerHandler(FindAllScoresQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindScoreByIdQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindScoresByWinnerQuery.class, this.queryHandler::handle);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void contextRefreshedEvent() {
        log.info("score-qry-service has successfully been started...");
    }
}
