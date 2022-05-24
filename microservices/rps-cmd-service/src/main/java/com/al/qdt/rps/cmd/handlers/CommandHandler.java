package com.al.qdt.rps.cmd.handlers;

import com.al.qdt.rps.cmd.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;

public interface CommandHandler {
    void handle(PlayGameCommand command);

    void handle(AddScoreCommand command);

    void handle(DeleteGameCommand command);

    void handle(RestoreDbCommand command);
}
