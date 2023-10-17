package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.ui.auth.RegisterPanel;
import com.soclosetoheaven.common.command.RegisterCommand;
import com.soclosetoheaven.common.exception.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.util.PasswordHasher;
import org.apache.commons.lang3.ArrayUtils;

import java.util.regex.Pattern;

public class UIRegisterCommand extends RegisterCommand {

    private final RegisterPanel panel;
    public UIRegisterCommand(RegisterPanel panel) {
        this.panel = panel;
    }


    @Override
    public Request toRequest(String[] args) throws InvalidAuthCredentialsException {
        String username = panel.readUsername();
        String login = panel.readLogin();
        char[] notHashedPassword = panel.readPassword();
        if (
                !Pattern.matches(User.NAME_PATTERN, username) ||
                !Pattern.matches(AuthCredentials.LOGIN_PATTERN, login) ||
                notHashedPassword.length < AuthCredentials.MIN_PASSWORD_SIZE ||
                notHashedPassword.length > AuthCredentials.MAX_PASSWORD_SIZE
        )
            throw new InvalidAuthCredentialsException(Messages.INVALID_AUTH_FORMAT.key);
        char[] password = PasswordHasher.hashMD2(notHashedPassword);
        AuthCredentials auth = new AuthCredentials(login, password);
        Request request = new Request(NAME, new RequestBody(ArrayUtils.toArray(username)));
        request.setAuthCredentials(auth);
        return request;
    }
}
