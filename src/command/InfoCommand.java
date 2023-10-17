package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

public class InfoCommand extends AbstractCommand{

    public InfoCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("info", io, fcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1)
            throw new InvalidCommandArgumentException(this.getName());
        getIO().writeln(TerminalColors.setColor(getFileCollectionManager().toString(),TerminalColors.BLUE));
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("info", TerminalColors.GREEN),
                TerminalColors.setColor(" - displays information about the collection",
                        TerminalColors.BLUE));
    }
}
