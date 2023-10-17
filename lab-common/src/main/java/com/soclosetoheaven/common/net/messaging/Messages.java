package com.soclosetoheaven.common.net.messaging;

public enum Messages {


    INVALID_AUTH_CREDENTIALS("invalid_auth_credentials"),
    INVALID_COMMAND_ARGUMENT("invalid_command_argument"),
    SUCCESSFULLY("successfully"),
    MANAGING_ERROR("managing_error"),
    UNKNOWN_COMMAND("unknown_command"),
    INVALID_REQUEST("invalid_request"),
    INVALID_ACCESS("invalid_access"),
    EXECUTING_SCRIPT_ERROR("executing_script_error"),
    EXECUTING_SCRIPT_RECURSION("executing_script_recursion"),
    UNSUCCESSFULLY("unsuccessfully"),
    NO_SUCH_ELEMENT("no_such_element"),
    REMOVED_ALL_POSSIBLE_ELEMENTS("removed_all_possible_elements"),
    SERVER_DOES_NOT_RESPOND("server_does_not_respond"),
    LOGGED_IN("logged_in"),
    LOGIN_TAKEN("login_taken"),
    EMPTY("empty"),
    INVALID_AUTH_FORMAT("invalid_auth_format"),
    INVALID_FIELD_VALUE("invalid_field_value");

    Messages(String key) {
        this.key = key;
    }

    public final String key;
}
