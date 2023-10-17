package com.soclosetoheaven.client.ui.command;

import com.soclosetoheaven.client.ui.auth.LoginPanel;
import com.soclosetoheaven.common.command.LoginCommand;
import com.soclosetoheaven.common.exception.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.util.PasswordHasher;

import java.util.regex.Pattern;

public class UILoginCommand extends LoginCommand {


    private final LoginPanel panel;

    public UILoginCommand(LoginPanel panel) {
        this.panel = panel;
    }

    @Override
    public Request toRequest(String[] args) throws InvalidAuthCredentialsException {
        String login = panel.readLogin();
        char[] notHashedPassword = panel.readPassword();
        if (
                !Pattern.matches(AuthCredentials.LOGIN_PATTERN, login) ||
                notHashedPassword.length < AuthCredentials.MIN_PASSWORD_SIZE ||
                notHashedPassword.length > AuthCredentials.MAX_PASSWORD_SIZE
        )
            throw new InvalidAuthCredentialsException(Messages.INVALID_AUTH_FORMAT.key);
        char[] password = PasswordHasher.hashMD2(notHashedPassword);
        Request request = new Request(NAME, new RequestBody(args));
        request.setAuthCredentials(new AuthCredentials(login, password));
        return request;
    }
}
