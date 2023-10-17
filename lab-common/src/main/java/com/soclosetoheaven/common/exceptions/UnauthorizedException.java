package com.soclosetoheaven.common.exceptions;

public class UnauthorizedException extends InvalidAuthCredentialsException{

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {}
}
