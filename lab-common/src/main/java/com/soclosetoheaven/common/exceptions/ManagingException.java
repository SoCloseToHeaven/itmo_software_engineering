package com.soclosetoheaven.common.exceptions;

import java.io.Serial;

public class ManagingException extends Exception{

    @Serial
    private static final long serialVersionUID = -3433872;

    public ManagingException(String message) {
        super(message);
    }
}
