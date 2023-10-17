package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import common.Dragon;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GroupCountingByCreationDateCommand extends AbstractCommand {

    public GroupCountingByCreationDateCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("group_counting_by_creation_date", io, fcm);
    }


    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1)
            throw new InvalidCommandArgumentException(this.getName());
        HashMap<Date, Integer> groups = new HashMap<>();
        ArrayList<Dragon> collection = getFileCollectionManager().getCollection();
        for (Dragon dragon : collection) {
            Date date = dragon.getCreationDate();
            if (!groups.containsKey(date))
                groups.put(date, 0);
            groups.replace(date, groups.get(date) + 1);
        }
        getIO().writeln(TerminalColors.setColor(groups.toString(), TerminalColors.BLUE));
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor(
                        "group_counting_by_creation_date",
                        TerminalColors.GREEN),
                TerminalColors.setColor(
                        " - splits collection to groups with the same creation date, then prints amount of them",
                        TerminalColors.BLUE));
    }
}
