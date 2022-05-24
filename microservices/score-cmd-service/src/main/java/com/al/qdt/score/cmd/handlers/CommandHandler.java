package com.al.qdt.score.cmd.handlers;

import com.al.qdt.score.cmd.commands.DeleteScoreCommand;

public interface CommandHandler {
    void handle(DeleteScoreCommand command);
}
