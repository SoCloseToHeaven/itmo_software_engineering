package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.ui.DialogManager;
import com.soclosetoheaven.client.ui.connection.UIConnectionManager;
import com.soclosetoheaven.common.command.*;
import com.soclosetoheaven.client.commandmanager.ClientCommandManager;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import org.apache.commons.lang3.ArrayUtils;

public class UIExecuteScriptCommand extends ExecuteScriptCommand {

    private final DialogManager dialogManager;

    private final UIConnectionManager connectionManager;

    private static final BasicIO io = new BasicIO();

    public UIExecuteScriptCommand(DialogManager dialogManager, UIConnectionManager connectionManager) {
        super(io);
        this.dialogManager = dialogManager;
        this.connectionManager = connectionManager;
        EXECUTE_SCRIPT_MANAGER.addCommand(new AddCommand());
        EXECUTE_SCRIPT_MANAGER.addCommand(new UpdateCommand());
        EXECUTE_SCRIPT_MANAGER.addCommand(new RemoveAllByAgeCommand());
        EXECUTE_SCRIPT_MANAGER.addCommand(new RemoveByIDCommand());
        EXECUTE_SCRIPT_MANAGER.addCommand(new CountLessThanAgeCommand());
        EXECUTE_SCRIPT_MANAGER.addCommand(new ClearCommand());
        EXECUTE_SCRIPT_MANAGER.addCommand(new ExecuteScriptCommand(io));
    }


    @Override
    public Request toRequest(String[] args) throws ManagingException {
        String path = dialogManager.showTextWithInput(LocaledUI.FILE_NAME.key);
        if (path == null)
            throw new InvalidCommandArgumentException();
        super.toRequest(ArrayUtils.toArray(path));
        String line;
        while ((line = io.read()) != null) {
            try {
                Request request = EXECUTE_SCRIPT_MANAGER.manage(line);
                if (request != null) {
                    connectionManager.manageRequest(request);
                }
            } catch (ManagingException e) {
                dialogManager.showText(e.getMessage());
            }
        }
        return null;
    }



    public final ClientCommandManager EXECUTE_SCRIPT_MANAGER = new ClientCommandManager();
}
