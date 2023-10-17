package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
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

import java.util.List;


public class RemoveAllByAgeCommand extends AbstractCommand {


    public static final String NAME = "remove_all_by_age";
    private final DragonCollectionManager collectionManager;

    private final UserManager userManager;
    public RemoveAllByAgeCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super(NAME);
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public RemoveAllByAgeCommand() {
        this(null,null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (args.length < MIN_ARGS_SIZE || !args[FIRST_ARG].chars().allMatch(Character::isDigit)) {
            throw new InvalidRequestException();
        }

        long age = Long.parseLong(args[FIRST_ARG]);

        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());

        List<Dragon> toDelete = collectionManager
                .getCollection().stream()
                .filter(elem -> elem.getCreatorId() == user.getID())
                .filter(elem -> elem.getAge() == age)
                .toList();
        collectionManager.removeAll(toDelete);
        return new Response(Messages.REMOVED_ALL_POSSIBLE_ELEMENTS.key);
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
