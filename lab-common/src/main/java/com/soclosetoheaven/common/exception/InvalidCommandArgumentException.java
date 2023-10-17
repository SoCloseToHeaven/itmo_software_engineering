package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

import java.io.Serial;
//client-side exception
public class InvalidCommandArgumentException extends ManagingException{

    @Serial
    private static final long serialVersionUID = -3657872;

    /**
     * default constructor
     */

    public InvalidCommandArgumentException(){
        super(Messages.INVALID_COMMAND_ARGUMENT.key);
    }

    /**
     * @param message exception message
     */

    public InvalidCommandArgumentException(String message) {
        super(message);
    }

    public InvalidCommandArgumentException(InvalidFieldValueException e) {
        super(e);
    }

}
