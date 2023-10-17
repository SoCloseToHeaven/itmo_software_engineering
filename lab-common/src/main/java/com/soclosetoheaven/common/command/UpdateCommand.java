package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.common.exception.*;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.*;
import org.apache.commons.lang3.ArrayUtils;


public class UpdateCommand extends AbstractCommand{


    public static final String NAME = "update";

    private final DragonCollectionManager collectionManager;

    private final BasicIO io;

    private final UserManager userManager;

    public UpdateCommand(DragonCollectionManager collectionManager, BasicIO io, UserManager userManager) {
        super(NAME);
        this.collectionManager = collectionManager;
        this.io = io;
        this.userManager = userManager;
    }

    public UpdateCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        this(collectionManager, null, userManager);
    }

    public UpdateCommand(BasicIO io) {
        this(null, io, null);
    }

    public UpdateCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws ManagingException{
        String[] args = requestBody.getArgs();
        if (
                !(requestBody instanceof RequestBodyWithDragon) ||
                args.length < MIN_ARGS_SIZE ||
                !args[FIRST_ARG].chars().allMatch(Character::isDigit)
        )
           throw new InvalidCommandArgumentException();

        int id = Integer.parseInt(args[FIRST_ARG]);

        Dragon dragon = collectionManager.getByID(id);
        if (dragon == null)
            throw new InvalidRequestException(Messages.NO_SUCH_ELEMENT.key);
        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());
        if (!user.isAdmin() && dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();

        if (!collectionManager.update(((RequestBodyWithDragon) requestBody).getDragon(), id))
            throw new InvalidRequestException(Messages.UNSUCCESSFULLY.key);
        return new Response(Messages.SUCCESSFULLY.key);
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        final int argsCount = Dragon.ARGS_COUNT + 1;
        final int dragonStartIndex = 1;
        if (args.length != argsCount )
            throw new InvalidCommandArgumentException();
        Dragon dragon;
        try {
            dragon = new Dragon(ArrayUtils.subarray(args, dragonStartIndex, args.length));
        } catch (InvalidFieldValueException | NumberFormatException e) {
            throw new InvalidRequestException();
        }
        return new Request(NAME, new RequestBodyWithDragon(args, dragon));
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "update {ID} {element}",
                " - updates data(in the same way as in {add} command) with the same ID"
        );
    }
}
