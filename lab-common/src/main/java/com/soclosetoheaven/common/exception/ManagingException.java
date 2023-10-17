package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

import java.io.Serial;

public class ManagingException extends Exception{

    @Serial
    private static final long serialVersionUID = -3433872;

    public ManagingException(String message) {
        super(message);
    }


    public ManagingException(Exception e) {
        super(e);
    }

    public ManagingException() {
        super(Messages.MANAGING_ERROR.key);
    }
}
