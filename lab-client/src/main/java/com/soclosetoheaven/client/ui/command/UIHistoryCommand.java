package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.ui.DialogManager;
import com.soclosetoheaven.common.command.HistoryCommand;
import com.soclosetoheaven.common.commandmanager.CommandManager;
import com.soclosetoheaven.common.net.messaging.Request;

public class UIHistoryCommand extends HistoryCommand {

    private final DialogManager dialogManager;

    public UIHistoryCommand(CommandManager<?,?> commandManager, DialogManager dialogManager) {
        super(commandManager);
        this.dialogManager = dialogManager;
    }
    @Override
    public Request toRequest(String[] args) {
        dialogManager.showText(getCommandManager().getHistory().toString());
        return null;
    }
}
