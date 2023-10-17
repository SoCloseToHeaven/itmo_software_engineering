package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.ui.application.ElementEditorPanel;
import com.soclosetoheaven.common.command.RemoveByIDCommand;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import org.apache.commons.lang3.ArrayUtils;

public class UIRemoveByIDCommand extends RemoveByIDCommand {

    private final ElementEditorPanel panel;

    public UIRemoveByIDCommand(ElementEditorPanel panel) {
        this.panel = panel;
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        String idStringValue = panel.getIDStringValue();
        if (idStringValue.isEmpty() || !idStringValue.chars().allMatch(Character::isDigit))
            throw new InvalidCommandArgumentException();
        return new Request(NAME, new RequestBody(ArrayUtils.toArray(idStringValue)));
    }
}
