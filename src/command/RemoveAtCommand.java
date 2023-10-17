package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

public class RemoveAtCommand extends AbstractCommand{

    public RemoveAtCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("remove_at", io, fcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length < 2 || !args[1].matches("\\d+"))
            throw new InvalidCommandArgumentException(this.getName());
        int parsedIndex = Integer.parseInt(args[1]);
        if (getFileCollectionManager().remove(parsedIndex))
            getIO().writeln(TerminalColors.setColor("Successfully removed element with index: %d".formatted(parsedIndex), TerminalColors.BLUE));
        else
            getIO().writeln(TerminalColors.setColor("Unsuccessfully removed element with index: %d".formatted(parsedIndex), TerminalColors.RED));

    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("remove_at {index}", TerminalColors.GREEN),
                TerminalColors.setColor(" - removes element with the same index from collection",
                        TerminalColors.BLUE));
    }
}
