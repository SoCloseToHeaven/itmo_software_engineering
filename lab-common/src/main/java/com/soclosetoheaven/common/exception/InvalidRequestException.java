package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

import java.io.Serial;
//server-side exception
public class InvalidRequestException extends ManagingException {
    @Serial
    private static final long serialVersionUID = -3667872;

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException() {
        super(Messages.INVALID_REQUEST.key);
    }
}
