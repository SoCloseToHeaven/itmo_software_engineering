package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exception.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.PasswordHasher;
import com.soclosetoheaven.common.util.TerminalColors;


public class LoginCommand extends AbstractCommand{

    public static final String NAME = "login";

    private final UserManager userManager;

    private final BasicIO io;

    public LoginCommand() {
        this(null, null);
    }
    public LoginCommand(UserManager userManager, BasicIO io) {
        super(NAME);
        this.userManager = userManager;
        this.io = io;
    }

    public LoginCommand(UserManager userManager) {
        this(userManager, null);
    }

    public LoginCommand(BasicIO io) {
        this(null, io);
    }

    @Override
    public Response execute(RequestBody requestBody) throws ManagingException {
        return userManager.login(requestBody);
    }



    @Override
    public Request toRequest(String[] args) throws InvalidAuthCredentialsException, InvalidCommandArgumentException {
        Request request = new Request(getName(), new RequestBody(args));
        io.writeln(TerminalColors.setColor("Enter login:", TerminalColors.GREEN));
        String login = System.console().readLine();
        io.writeln(TerminalColors.setColor("Enter password:", TerminalColors.GREEN));
        char[] password = System.console().readPassword();
        if (login == null || password == null)
            throw new InvalidCommandArgumentException();
        request.setAuthCredentials(new AuthCredentials(login, PasswordHasher.hashMD2(password)));
        return request;
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "login",
                " - logins registered user"
        );
    }
}
