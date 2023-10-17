package com.soclosetoheaven.common.exception;

import com.soclosetoheaven.common.net.messaging.Messages;

/**
 * exception that is used when something is wrong with executing script
 */
public class ExecutingScriptException extends ManagingException{

    /**
     * @param message exception message
     */

    public ExecutingScriptException(String message) {
        super(message);
    }


    public ExecutingScriptException() {
        super(Messages.EXECUTING_SCRIPT_ERROR.key);
    }
}
