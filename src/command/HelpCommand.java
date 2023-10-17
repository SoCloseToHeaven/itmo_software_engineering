package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import commandmanagers.BasicCommandManager;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

public class HelpCommand extends AbstractCommand{

    public HelpCommand(BasicClientIO io, FileCollectionManager fcm, BasicCommandManager bcm) {
        super("help", io, fcm, bcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1)
            throw new InvalidCommandArgumentException(this.getName());
        getBasicCommandManager().getCommands().forEach((name, command) -> getIO().writeln(command.getUsage()));
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("help", TerminalColors.GREEN),
                TerminalColors.setColor(" - provides information for all usable commands",
                        TerminalColors.BLUE));
    }
}
