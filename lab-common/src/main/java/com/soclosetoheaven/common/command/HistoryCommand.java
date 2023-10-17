package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.commandmanager.CommandManager;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class HistoryCommand extends AbstractCommand{

    private final CommandManager<?,?> commandManager;
    private final BasicIO io;

    public static final String NAME = "history";

    public HistoryCommand(CommandManager<?,?> commandManager, BasicIO io) {
        super(NAME);
        this.commandManager = commandManager;
        this.io = io;
    }

    public HistoryCommand() {
        this(null, null);
    }

    public HistoryCommand(CommandManager<?,?> commandManager) {
        this(commandManager, null);
    }

    @Override
    public Response execute(RequestBody requestBody) {
        return new Response(commandManager.getHistory().toString());
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(args);
    }

    @Override
    public String getUsage() {
        return "%s - %s %s %s".formatted(
                "history",
                "displays",
                commandManager.getHistory().getMaxSize(),
                "recently used commands"
        );
    }

    protected CommandManager<?,?> getCommandManager() {
        return commandManager;
    }
}
