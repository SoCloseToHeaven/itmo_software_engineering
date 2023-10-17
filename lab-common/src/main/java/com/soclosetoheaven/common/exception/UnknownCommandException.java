package com.soclosetoheaven.common.exception;

/**
 * Used when command manager can't find command
 */
public class UnknownCommandException extends ManagingException {

    /**
     * @param name command's name
     */

    public UnknownCommandException(String name) {
        super(name);
    }
}
