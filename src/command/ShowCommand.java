package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import common.Dragon;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

import java.util.ArrayList;

public class ShowCommand extends AbstractCommand{

    public ShowCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("show", io, fcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1)
            throw new InvalidCommandArgumentException(this.getName());
        ArrayList<Dragon> collection = getFileCollectionManager().getCollection();
        if (collection.size() == 0)
            getIO().writeln(TerminalColors.setColor("No elements found in collection", TerminalColors.BLUE));
        else
            collection.forEach(element -> getIO().writeln(element.toString()));
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("show", TerminalColors.GREEN),
                TerminalColors.setColor(" - displays all elements of collection with their data",
                        TerminalColors.BLUE));
    }
}
