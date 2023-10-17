package com.soclosetoheaven.client.locale;

public enum LocaledUI {

    NAME("name"),
    X_COORDINATE("x_cord"),
    Y_COORDINATE("y_cord"),
    WINGSPAN("wingspan"),
    TREASURES("treasures"),
    AGE("age"),
    ID("id"),
    DEPTH("depth"),
    DATE("date"),
    DESCRIPTION("description"),
    CREATOR_ID("creator_id"),
    TYPE("type"),

    LOGIN("login"),
    PASSWORD("password"),
    CREATE("create"),
    DELETE("delete"),
    UPDATE("update"),
    TABLE("table"),
    MAP("map"),
    FILTER("filter"),
    BACK("back"),
    REGISTER("register"),
    REMOVE_ALL_BY_AGE("remove_all_by_age"),
    CLEAR("clear"),
    HISTORY("history"),
    HELP("help"),
    EXECUTE_SCRIPT("execute_script"),
    OPTIONS("options"),
    LOGOUT("logout"),
    SIGN_IN("sign_in"),
    SIGN_UP("sign_up"),
    MESSAGE("message"),
    INPUT("input"),
    FILE_NAME("file_name"),
    THEME_SETTINGS("theme_settings"),
    GETTING_RESPONSE_ERROR("getting_response_error"),
    SENDING_DATA_ERROR("sending_data_error"),
    MANAGING_DATA_ERROR("managing_data_error"),
    EXECUTE_SCRIPT_HELP("execute_script_help");


    LocaledUI(String key) {
        this.key = key;
    }

    public String key;
}
