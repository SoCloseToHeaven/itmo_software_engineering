package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.InvalidFieldValueException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.collectionmanager.DragonCollectionManager;
import com.soclosetoheaven.common.exception.InvalidRequestException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.*;

public class AddCommand extends AbstractCommand{

    public static final String NAME = "add";

    private final DragonCollectionManager collectionManager;

    private final BasicIO io;
    private final UserManager userManager;

    public AddCommand(DragonCollectionManager collectionManager, BasicIO io, UserManager userManager) {
        super(NAME);
        this.collectionManager = collectionManager;
        this.io = io;
        this.userManager = userManager;
    }

    public AddCommand(BasicIO io) {
        this(null, io, null);
    }

    public AddCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        this(collectionManager,null, userManager);
    }

    public AddCommand() {
        this(null, null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        if (!(requestBody instanceof RequestBodyWithDragon))
            throw new InvalidRequestException();
        Dragon dragon = ((RequestBodyWithDragon) requestBody).getDragon();
        dragon.setCreatorId(userManager.getUserByAuthCredentials(requestBody.getAuthCredentials()).getID());
        if (!collectionManager.add(dragon))
            throw new InvalidRequestException(Messages.UNSUCCESSFULLY.key);
        return new Response(Messages.SUCCESSFULLY.key);
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length != Dragon.ARGS_COUNT)
            throw new InvalidCommandArgumentException("Неверные аргументы");
        Dragon dragon;
        try {
            dragon = new Dragon(args);
        } catch (InvalidFieldValueException | NumberFormatException e) {
            throw new ManagingException("Неверные данные при создании дракона");
        }
        return new Request(NAME, new RequestBodyWithDragon(args, dragon));
    }

    @Override
    public String getUsage() {
        return "adds new collection element, fill the fields with values line by line";
    }
}
