package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.exceptions.UnauthorizedException;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class LogoutCommand extends AbstractCommand{


    public LogoutCommand() {
        super("logout");
    }

    @Override
    public Response execute(RequestBody requestBody) throws ManagingException {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        throw new UnauthorizedException();
    }

    @Override
    String getUsage() {
        return "logout - is used to logout from your sign";
    }
}
