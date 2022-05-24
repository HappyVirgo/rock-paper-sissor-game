package com.al.qdt.rps.cmd;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;
import com.al.qdt.rps.cmd.handlers.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class RpsCmdServiceApp {
    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(RpsCmdServiceApp.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        this.commandDispatcher.registerHandler(PlayGameCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(AddScoreCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(DeleteGameCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(RestoreDbCommand.class, this.commandHandler::handle);
    }
}
