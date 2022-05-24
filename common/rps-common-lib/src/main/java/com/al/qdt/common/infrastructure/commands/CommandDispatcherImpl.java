package com.al.qdt.common.infrastructure.commands;

import com.al.qdt.common.exceptions.DispatcherException;
import com.al.qdt.cqrs.commands.BaseCommand;
import com.al.qdt.cqrs.commands.CommandHandlerMethod;
import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CommandDispatcherImpl implements CommandDispatcher {
    public static final String NO_COMMAND_HANDLER_REGISTERED_EXCEPTION_MESSAGE = "No command handler was registered!";
    public static final String SEND_COMMAND_EXCEPTION_MESSAGE = "Cannot send command to more than one handler!";

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        final var handlers = this.routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        final var handlers = this.routes.get(command.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new DispatcherException(NO_COMMAND_HANDLER_REGISTERED_EXCEPTION_MESSAGE);
        }
        if (handlers.size() > 1) {
            throw new DispatcherException(SEND_COMMAND_EXCEPTION_MESSAGE);
        }
        handlers.get(0).handle(command);
    }
}
