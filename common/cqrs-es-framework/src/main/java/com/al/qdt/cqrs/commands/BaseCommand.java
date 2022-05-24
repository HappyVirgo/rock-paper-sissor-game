package com.al.qdt.cqrs.commands;

import com.al.qdt.cqrs.messages.Message;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public abstract class BaseCommand extends Message {
}
