package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

public class InvalidAuthCredentialsException extends InvalidRequestException{

    public InvalidAuthCredentialsException() {
        super(Messages.INVALID_AUTH_CREDENTIALS.key);
    }

    public InvalidAuthCredentialsException(String message) {
        super(message);
    }
}
