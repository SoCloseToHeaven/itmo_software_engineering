package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.commandmanager.CommandManager;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class HelpCommand extends AbstractCommand{


    public static final String NAME = "help";

    protected final CommandManager<?,?> commandManager;

    public HelpCommand(CommandManager<?,?> commandManager) {
        super(NAME);
        this.commandManager = commandManager;
    }

    public HelpCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException();
    }


    @Override
    public String getUsage() {
        return "provides information for all usable commands";
    }
}
