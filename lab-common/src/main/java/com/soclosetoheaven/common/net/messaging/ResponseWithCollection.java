package com.soclosetoheaven.common.net.messaging;

import com.soclosetoheaven.common.model.Dragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ResponseWithCollection extends Response{

    @Serial
    private final static long serialVersionUID = 958884;
    private List<Dragon> collection;

    public ResponseWithCollection(List<Dragon> collection) {
        super(null);
        this.collection = collection;
    }

    public List<Dragon> getCollection() {
        return collection;
    }
}
