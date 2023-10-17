package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.ui.DialogManager;
import com.soclosetoheaven.common.command.HelpCommand;
import com.soclosetoheaven.common.commandmanager.CommandManager;
import com.soclosetoheaven.common.net.messaging.Request;

public class UIHelpCommand extends HelpCommand {


    private final DialogManager dialogManager;

    public UIHelpCommand(CommandManager<?,?> commandManager, DialogManager dialogManager) {
        super(commandManager);
        this.dialogManager = dialogManager;
    }
    @Override
    public Request toRequest(String[] args) {
        dialogManager.showText(LocaledUI.EXECUTE_SCRIPT_HELP.key);
        return null;
    }
}
