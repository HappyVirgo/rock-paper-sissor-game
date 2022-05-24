package com.al.qdt.cqrs.infrastructure;

import com.al.qdt.cqrs.commands.BaseCommand;
import com.al.qdt.cqrs.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    <T extends BaseCommand> void send(T command);
}
