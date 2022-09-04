package com.al.qdt.rps.cmd.api.commands;

import com.al.qdt.cqrs.commands.BaseCommand;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
public class DeleteGameCommand extends BaseCommand {
}
