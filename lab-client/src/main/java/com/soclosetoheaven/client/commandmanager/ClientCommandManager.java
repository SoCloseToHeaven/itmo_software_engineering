package com.soclosetoheaven.client.commandmanager;

import com.soclosetoheaven.common.command.*;
import com.soclosetoheaven.common.commandmanager.CommandManager;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.exception.UnknownCommandException;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.util.LRUCache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientCommandManager implements CommandManager<Request, String> {

    private static final int MAX_HISTORY_SIZE = 13;

    private final LRUCache<AbstractCommand> history;
    private final HashMap<String, AbstractCommand> commands;

    private static final int ARGS_START_POSITION = 1;

    public ClientCommandManager() {
        commands = new HashMap<>();
        history = new LRUCache<>(MAX_HISTORY_SIZE);
    }

    @Override
    public Request manage(String inputLine) throws ManagingException {
        String[] args = inputLine.trim().split("\\s+");
        String commandName = args[AbstractCommand.FIRST_ARG].toLowerCase();
        args = Arrays.copyOfRange(args, ARGS_START_POSITION, args.length);
        AbstractCommand command;
        if ((command = commands.get(commandName)) == null)
            throw new UnknownCommandException(Messages.UNKNOWN_COMMAND.key);
        history.add(command);
        return command.toRequest(args);
    }

    @Override
    public Map<String, ? extends AbstractCommand> getCommands() {
        return commands;
    }

    @Override
    public void addCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }



    public LRUCache<AbstractCommand> getHistory() {
        return this.history;
    }
}
