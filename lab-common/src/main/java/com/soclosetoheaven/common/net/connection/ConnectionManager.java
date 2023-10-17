package com.soclosetoheaven.common.net.connection;

import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;

public interface ConnectionManager {

    Response manageRequest(Request request) throws ManagingException;

    void manageResponse();


    void setAuthCredentials(AuthCredentials authCredentials);
}
