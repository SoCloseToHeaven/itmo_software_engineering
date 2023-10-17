package com.soclosetoheaven.common.net.factories;

import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.models.factories.DragonFactory;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.RequestBodyWithDragon;

public class RequestBodyFactory {

    private RequestBodyFactory() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static RequestBody createRequestBody(String[] args) {
        return new RequestBody(args);
    }

    public static RequestBody createRequestBodyWithDragon(String[] args, BasicIO io) {
        return new RequestBodyWithDragon(args, DragonFactory.createDragon(io));
    }
}
