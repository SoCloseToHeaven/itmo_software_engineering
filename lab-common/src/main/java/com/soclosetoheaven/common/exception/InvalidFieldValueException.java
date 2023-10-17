package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

/**
 * used when field value in class is incorrect
 */
public class InvalidFieldValueException extends RuntimeException {

    /**
     * @param message exception message
     */
    public InvalidFieldValueException(String message) {
        super(message);
    }

    public InvalidFieldValueException() {
        super(Messages.INVALID_FIELD_VALUE.key);
    }
}
