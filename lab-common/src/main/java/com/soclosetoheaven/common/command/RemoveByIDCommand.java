package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.common.exception.InvalidAccessException;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.InvalidRequestException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class RemoveByIDCommand extends AbstractCommand{

    public static final String NAME = "remove_by_id";

    private final DragonCollectionManager collectionManager;

    private final UserManager userManager;
    public RemoveByIDCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super(NAME);
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public RemoveByIDCommand() {
        this(null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (args.length < MIN_ARGS_SIZE || !args[FIRST_ARG].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();
        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());
        int id = Integer.parseInt(args[FIRST_ARG]);
        Dragon dragon = collectionManager.getByID(id);
        if (dragon == null)
            throw new InvalidRequestException(Messages.NO_SUCH_ELEMENT.key);
        if (!user.isAdmin() && dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (!collectionManager.removeByID(id))
            throw new InvalidRequestException(Messages.UNSUCCESSFULLY.key);
        return new Response(Messages.SUCCESSFULLY.key);
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length >= MIN_ARGS_SIZE && args[FIRST_ARG].chars().allMatch(Character::isDigit))
            return super.toRequest(args);
        throw new InvalidCommandArgumentException();
    }

    public String getUsage() {
        return "%s%s".formatted(
                "remove_all_by_age {age}",
                " - removes all collection elements with the same age"
        );
    }
}
