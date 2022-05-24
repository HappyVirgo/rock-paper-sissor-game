package com.al.qdt.score.cmd.commands;

import com.al.qdt.cqrs.commands.BaseCommand;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
public class DeleteScoreCommand extends BaseCommand {
}
