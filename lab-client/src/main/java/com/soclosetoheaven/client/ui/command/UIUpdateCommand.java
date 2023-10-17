package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.ui.application.ElementEditorPanel;
import com.soclosetoheaven.common.command.UpdateCommand;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBodyWithDragon;
import org.apache.commons.lang3.ArrayUtils;

public class UIUpdateCommand extends UpdateCommand {

    private final ElementEditorPanel panel;
    public UIUpdateCommand(ElementEditorPanel panel) {
        this.panel = panel;
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        String idStringValue = panel.getIDStringValue();
        if (idStringValue.isEmpty() || !idStringValue.chars().allMatch(Character::isDigit))
            throw new InvalidCommandArgumentException();
        Dragon dragon;
        dragon = panel.readDragon();
        if (dragon == null)
            throw new InvalidCommandArgumentException(Messages.INVALID_FIELD_VALUE.key);
        return new Request(NAME, new RequestBodyWithDragon(ArrayUtils.toArray(idStringValue), dragon));
    }
}
