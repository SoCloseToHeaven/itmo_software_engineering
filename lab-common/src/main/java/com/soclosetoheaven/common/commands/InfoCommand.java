package com.soclosetoheaven.common.commands;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class InfoCommand extends AbstractCommand{

    private final FileCollectionManager cm;
    public InfoCommand(FileCollectionManager cm) {
        super("info");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        return new Response(cm.toString());
    }

    @Override
    public Request toRequest(String[] args) {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "info",
                " - displays information about the collection"
        );
    }
}
