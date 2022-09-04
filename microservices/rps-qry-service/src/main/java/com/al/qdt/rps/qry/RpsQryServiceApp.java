package com.al.qdt.rps.qry;

import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.rps.qry.api.queries.FindAllGamesQuery;
import com.al.qdt.rps.qry.api.queries.FindGameByIdQuery;
import com.al.qdt.rps.qry.api.queries.FindGamesByUsernameQuery;
import com.al.qdt.rps.qry.infrastructure.handlers.QueryHandler;
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
public class RpsQryServiceApp {
    private final QueryDispatcher queryDispatcher;

    private final QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(RpsQryServiceApp.class, args);
    }

    /**
     * Registering queries to appropriate handlers.
     */
    @PostConstruct
    public void registerHandlers() {
        // Queries. These queries return a result and do not change the state of the system, and they are free of side effects.
        this.queryDispatcher.registerHandler(FindAllGamesQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindGameByIdQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindGamesByUsernameQuery.class, this.queryHandler::handle);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void contextRefreshedEvent() {
        log.info("rps-qry-service has successfully been started...");
    }
}
