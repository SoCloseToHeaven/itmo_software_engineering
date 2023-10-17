package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.ui.DialogManager;
import com.soclosetoheaven.common.command.RemoveAllByAgeCommand;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import org.apache.commons.lang3.ArrayUtils;

public class UIRemoveAllByAgeCommand extends RemoveAllByAgeCommand {

    private final DialogManager dialogManager;
    public UIRemoveAllByAgeCommand(DialogManager dialogManager) {
        this.dialogManager = dialogManager;
    }

    @Override
    public Request toRequest(String[] args) throws InvalidCommandArgumentException {
        String ageTextValue = dialogManager.showTextWithInput(LocaledUI.AGE.key);
        if (ageTextValue == null || !ageTextValue.chars().allMatch(Character::isDigit))
            throw new InvalidCommandArgumentException();
        return new Request(NAME, new RequestBody(ArrayUtils.toArray(ageTextValue)));
    }
}
