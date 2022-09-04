package com.al.qdt.rps.cmd;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.api.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.api.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.api.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.api.commands.RestoreDbCommand;
import com.al.qdt.rps.cmd.infrastructure.handlers.CommandHandler;
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
public class RpsCmdServiceApp {
    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(RpsCmdServiceApp.class, args);
    }

    /**
     * Registering commands to appropriate handlers.
     */
    @PostConstruct
    public void registerHandlers() {
        // Commands. These commands change the state of a system.
        this.commandDispatcher.registerHandler(PlayGameCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(AddScoreCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(DeleteGameCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(RestoreDbCommand.class, this.commandHandler::handle);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void contextRefreshedEvent() {
        log.info("rps-cmd-service has successfully been started...");
    }
}
