package com.al.qdt.rps.cmd.commands;

import com.al.qdt.common.enums.Player;
import com.al.qdt.cqrs.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Value
@SuperBuilder
@AllArgsConstructor
public class AddScoreCommand extends BaseCommand {
    public static final String WINNER_MUST_NOT_BE_NULL = "Winner must not be null";

    @NotNull(message = WINNER_MUST_NOT_BE_NULL)
    Player winner;
}
