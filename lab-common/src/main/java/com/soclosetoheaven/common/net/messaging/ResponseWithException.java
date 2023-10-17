package com.soclosetoheaven.common.net.messaging;


import com.soclosetoheaven.common.exception.ManagingException;

import java.io.Serial;

public class ResponseWithException extends Response {

    @Serial
    private final static long serialVersionUID = 9513113;

    private final ManagingException e; // Exception implements serializable
    public ResponseWithException(String description, ManagingException e) {
        super(description);
        this.e = e;
    }

    public ResponseWithException(ManagingException e) {
        this("SERVER RESPONDED WITH EXCEPTION", e);
    }

    public ManagingException getException() {
        return this.e;
    }

    @Override
    public String toString() {
        return e.getMessage();
    }
}
