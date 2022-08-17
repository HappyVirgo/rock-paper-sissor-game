package com.al.qdt.score.qry;

import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.score.qry.handlers.QueryHandler;
import com.al.qdt.score.qry.queries.FindAllScoresQuery;
import com.al.qdt.score.qry.queries.FindScoreByIdQuery;
import com.al.qdt.score.qry.queries.FindScoresByWinnerQuery;
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

    @PostConstruct
    public void registerHandlers() {
        this.queryDispatcher.registerHandler(FindAllScoresQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindScoreByIdQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindScoresByWinnerQuery.class, this.queryHandler::handle);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void contextRefreshedEvent() {
        log.info("score-qry-service has successfully been started...");
    }
}
