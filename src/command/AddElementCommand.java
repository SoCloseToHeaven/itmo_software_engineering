package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import common.Coordinates;
import common.Dragon;
import common.DragonCave;
import common.DragonType;
import exceptions.InvalidCommandArgumentException;
import util.TerminalColors;

public class AddElementCommand extends CollectionElementEditorCommand {

    public AddElementCommand(BasicClientIO io, FileCollectionManager fcm) {
        super("add", io, fcm);
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException { //rebuild later
        if (args.length != 1)
            throw new InvalidCommandArgumentException(this.getName());
        String name = inputName();
        Integer x = inputX();
        double y = inputY();
        Coordinates cords = new Coordinates(x,y);
        Long age = inputAge();
        String description = inputDescription();
        Integer wingspan = inputWingspan();
        long depth = inputDepth();
        int numberOfTreasures = inputNumberOfTreasures();
        DragonCave cave = new DragonCave(depth, numberOfTreasures);
        DragonType type = inputDragonType();
        Dragon dragon = new Dragon(name, cords, age, description, wingspan, type, cave);
        getFileCollectionManager().add(dragon);
        getIO().writeln(TerminalColors.setColor("Element added successfully",TerminalColors.BLUE));
    }


    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("add {element}", TerminalColors.GREEN),
                TerminalColors.setColor(" - adds new collection element, fill the fields with values line by line",
                TerminalColors.BLUE));
    }
}
