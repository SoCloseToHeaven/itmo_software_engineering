package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.InvalidRequestException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class CountLessThanAgeCommand extends AbstractCommand {


    public static final String NAME = "count_less_than_age";

    private final DragonCollectionManager collectionManager;
    public CountLessThanAgeCommand(DragonCollectionManager collectionManager) {
        super(NAME);
        this.collectionManager = collectionManager;
    }

    public CountLessThanAgeCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody)  throws InvalidRequestException {
        String[] args = requestBody.getArgs();
        if (args.length < MIN_ARGS_SIZE || !args[FIRST_ARG].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();
        Long age = Long.parseLong(requestBody.getArgs()[FIRST_ARG]);
        Long count = collectionManager.getCollection().stream().filter(elem -> elem.getAge() < age).count();
        return new Response(Messages.REMOVED_ALL_POSSIBLE_ELEMENTS.key);
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length >= MIN_ARGS_SIZE && args[FIRST_ARG].chars().allMatch(Character::isDigit))
            return super.toRequest(args);
        throw new InvalidCommandArgumentException();
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "count_less_than_age {age}",
                " - counts elements of collection which age is lower than {age}(can't be null)"
        );
    }
}
