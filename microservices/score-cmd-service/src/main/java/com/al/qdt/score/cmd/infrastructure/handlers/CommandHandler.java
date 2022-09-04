package com.al.qdt.score.cmd.infrastructure.handlers;

import com.al.qdt.score.cmd.api.commands.DeleteScoreCommand;

public interface CommandHandler {
    void handle(DeleteScoreCommand command);
}
