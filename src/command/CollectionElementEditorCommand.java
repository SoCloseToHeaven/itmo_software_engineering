package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import common.Coordinates;
import common.Dragon;
import common.DragonCave;
import common.DragonType;
import exceptions.InvalidFieldValueException;
import util.TerminalColors;

/**
 * abstract class for commands that add or update collection elements
 */
public abstract class CollectionElementEditorCommand extends AbstractCommand {

    private final BasicClientIO localIO;

    /**
     * @param name of command
     * @param io - input-output manager
     * @param fcm - manager of collection
     */
    public CollectionElementEditorCommand(String name, BasicClientIO io, FileCollectionManager fcm) {
        super(name, io, fcm);
        this.localIO = io;
    }

    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public String inputName() {
        try {
            String name = localIO.stdRead("Type element's name: ");
            Dragon.VALIDATOR.validateName(name);
            return name;
        } catch (InvalidFieldValueException e) {
            localIO.writeln(e.getMessage());
            return inputName();
        }
    }

    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public Integer inputX() {
        try {
            Integer x = Integer.parseInt(localIO.stdReadLineWithNull("Type coordinate X: "));
            Coordinates.VALIDATOR.validateX(x);
            return x;
        } catch (NumberFormatException|InvalidFieldValueException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputX();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public double inputY() {
        try {
            double y = Double.parseDouble(localIO.stdRead("Type coordinate Y: "));
            Coordinates.VALIDATOR.validateY(y);
            return y;
        } catch (NumberFormatException|InvalidFieldValueException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputY();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public Long inputAge() {
        try {
            String ageString = localIO.stdReadLineWithNull("Type age: "); //age can be null
            Long age = (ageString == null) ? null : Long.parseLong(ageString);
            Dragon.VALIDATOR.validateAge(age);
            return age;
        } catch (NumberFormatException|InvalidFieldValueException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputAge();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public String inputDescription() {
        try {
            String description = localIO.stdReadLineWithNull("Type description: ");//description
            Dragon.VALIDATOR.validateDescription(description);
            return description;
        } catch (InvalidFieldValueException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputDescription();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public Integer inputWingspan() {
        try {
            Integer wingspan = Integer.parseInt(localIO.stdReadLineWithNull("Type wingspan: "));//wingspan
            Dragon.VALIDATOR.validateWingspan(wingspan);
            return wingspan;
        } catch (InvalidFieldValueException|NumberFormatException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputWingspan();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public long inputDepth() {
        try {
            return Long.parseLong(localIO.stdRead("Type cave's depth: "));
        } catch (NumberFormatException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputDepth();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public int inputNumberOfTreasures() {
        try {
            int numberOfTreasures = Integer.parseInt(localIO.stdRead("Type number of treasures: "));//CaveNumberOfTreasures
            DragonCave.VALIDATOR.validateNumberOfTreasures(numberOfTreasures);
            return numberOfTreasures;
        } catch (NumberFormatException|InvalidFieldValueException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputNumberOfTreasures();
        }
    }
    /**
     * validates inputted value and returns it
     * @return inputted value
     */
    public DragonType inputDragonType() {
        try {
            return DragonType.parseDragonType(localIO.stdRead(
                    "%s(%s): ".formatted("Type dragon's type", DragonType.stringValues())));
        } catch (UnsupportedOperationException e) {
            localIO.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputDragonType();
        }
    }
}
