package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class LogoutCommand extends AbstractCommand{


    public static final String NAME = "logout";
    public LogoutCommand() {
        super(NAME);
    }

    @Override
    public Response execute(RequestBody requestBody) throws ManagingException {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) {
        return null;
    }

    @Override
    public String getUsage() {
        return "logout - is used to logout from your sign";
    }
}
