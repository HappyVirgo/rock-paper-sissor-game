package com.al.qdt.score.cmd;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.score.cmd.commands.DeleteScoreCommand;
import com.al.qdt.score.cmd.handlers.CommandHandler;
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
public class ScoreCmdServiceApp {
    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(ScoreCmdServiceApp.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        this.commandDispatcher.registerHandler(DeleteScoreCommand.class, this.commandHandler::handle);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void contextRefreshedEvent() {
        log.info("score-cmd-service has successfully been started...");
    }
}
