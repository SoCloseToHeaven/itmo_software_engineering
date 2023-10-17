package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.ui.application.ElementEditorPanel;
import com.soclosetoheaven.common.command.AddCommand;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.InvalidFieldValueException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBodyWithDragon;

public class UIAddCommand extends AddCommand {

    private final ElementEditorPanel panel;
    public UIAddCommand(ElementEditorPanel panel) {
        this.panel = panel;
    }


    @Override
    public Request toRequest(String[] args) throws ManagingException {
        Dragon dragon;
        dragon = panel.readDragon();
        if (dragon == null)
            throw new InvalidCommandArgumentException(Messages.INVALID_FIELD_VALUE.key); // TODO: add localized info
        return new Request(NAME, new RequestBodyWithDragon(args, dragon));
    }
}
