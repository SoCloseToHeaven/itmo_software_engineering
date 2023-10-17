package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

public class RemoveByIDCommand extends AbstractCommand{

    public RemoveByIDCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("remove_by_id", io, fcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 2 || !args[1].matches("[1-9]\\d*"))
            throw new InvalidCommandArgumentException(this.getName());
        long parsedID = Long.parseLong(args[1]);
        if (getFileCollectionManager().removeIf(parsedID))
            getIO().writeln(
                    TerminalColors.setColor("Successfully removed element with index: %d".formatted(parsedID),
                            TerminalColors.BLUE));
        else
            getIO().writeln(
                    TerminalColors.setColor("Unsuccessfully removed element with index: %d".formatted(parsedID),
                            TerminalColors.RED));
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("remove_by_id {id}", TerminalColors.GREEN),
                TerminalColors.setColor(" - removes element out of collection with the same ID",
                        TerminalColors.BLUE));
    }
}
