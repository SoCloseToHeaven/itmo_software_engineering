package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.common.exception.InvalidAccessException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.List;

public class ClearCommand extends AbstractCommand{

    public static final String NAME = "clear";

    private final DragonCollectionManager collectionManager;
    private final UserManager userManager;
    public ClearCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super(NAME);
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public ClearCommand() {
        this(null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidAccessException{
        List<Dragon> dragons = collectionManager.getCollection();


        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());

        List<Dragon> toDelete = dragons
                .stream()
                .filter(elem -> elem.getCreatorId() == user.getID())
                .toList();

        collectionManager.removeAll(toDelete);

        return new Response(Messages.REMOVED_ALL_POSSIBLE_ELEMENTS.key);
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "clear",
                " - removes all elements out of collection"
        );
    }
}
