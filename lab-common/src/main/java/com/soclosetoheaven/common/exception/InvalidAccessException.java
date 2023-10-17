package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

public class InvalidAccessException extends InvalidRequestException{

    public InvalidAccessException() {
        super(Messages.INVALID_ACCESS.key);
    }
}
