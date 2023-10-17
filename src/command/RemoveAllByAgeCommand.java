package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

import java.util.Objects;

public class RemoveAllByAgeCommand extends AbstractCommand{

    public RemoveAllByAgeCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("remove_all_by_age", io, fcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length == 2 && !args[1].matches("[1-9]\\d*"))
            throw new InvalidCommandArgumentException(this.getName());
        Long parsedAge = (args.length <= 1) ? null : Long.parseLong(args[1]);
        if (getFileCollectionManager().getCollection().removeIf(element -> Objects.equals(element.getAge(), parsedAge)))
            getIO().writeln(TerminalColors.setColor(
                    "%s - %s".formatted("Successfully removed elements with age", parsedAge),
                    TerminalColors.BLUE));
        else
            getIO().writeln(TerminalColors.setColor(
                    "%s - %s".formatted("No elements found with age", parsedAge),
                    TerminalColors.RED));
    }
    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("remove_all_by_age {age}", TerminalColors.GREEN),
                TerminalColors.setColor(" - removes all collection elements with the same age",
                        TerminalColors.BLUE));
    }
}
