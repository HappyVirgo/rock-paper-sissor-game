package com.al.qdt.score.cmd;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.score.cmd.commands.DeleteScoreCommand;
import com.al.qdt.score.cmd.handlers.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

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
}
