package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanager.CollectionManager;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.net.messaging.ResponseWithCollection;

public class ShowCommand extends AbstractCommand{

    public static final String NAME = "show";

    private CollectionManager<Dragon> collectionManager;

    public ShowCommand(CollectionManager<Dragon> collectionManager) {
        super(NAME);
        this.collectionManager = collectionManager;
    }
    public ShowCommand() {
        super(NAME);
    };
    @Override
    public Response execute(RequestBody requestBody){
        return new ResponseWithCollection(collectionManager.getCollection());
    }

    @Override
    public String getUsage() { // TODO: refactor later
        return null;
    }
}
