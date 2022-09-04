package com.al.qdt.rps.cmd.infrastructure.handlers;

import com.al.qdt.rps.cmd.api.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.api.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.api.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.api.commands.RestoreDbCommand;

public interface CommandHandler {
    void handle(PlayGameCommand command);

    void handle(AddScoreCommand command);

    void handle(DeleteGameCommand command);

    void handle(RestoreDbCommand command);
}
