package com.al.qdt.rps.cmd.commands;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.cqrs.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@SuperBuilder
@AllArgsConstructor
public class PlayGameCommand extends BaseCommand {
    public static final String USERNAME_MUST_NOT_BE_BLANK = "Username must not be null or empty";
    public static final String HAND_MUST_NOT_BE_NULL = "Hand must not be null";

    @NotBlank(message = USERNAME_MUST_NOT_BE_BLANK)
    String username;

    @NotNull(message = HAND_MUST_NOT_BE_NULL)
    Hand hand;
}
