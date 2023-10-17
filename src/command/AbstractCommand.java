package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import commandmanagers.BasicCommandManager;
import exceptions.InvalidCommandArgumentException;

import java.util.Objects;

/**
 * Provided to do some actions while calling method {@link #execute(String[])}
 */
public abstract class AbstractCommand {

    private final String name;
    private final BasicClientIO io;
    private final FileCollectionManager fcm;

    private BasicCommandManager bcm;

    /**
     *
     * @param name of command
     * @param io input-output handler
     * @param fcm to provide working with collection
     */
    public AbstractCommand(String name, BasicClientIO io, FileCollectionManager fcm) {
        this.name = name;
        this.io = io;
        this.fcm = fcm;
    }

    /**
     *
     * @param name of command
     * @param io input-output handler
     * @param fcm to provide working with collection
     * @param bcm to execute commands
     */

    public AbstractCommand(String name, BasicClientIO io, FileCollectionManager fcm, BasicCommandManager bcm) {
        this.name = name;
        this.io = io;
        this.fcm = fcm;
        this.bcm = bcm;
    }

    /**
     * execute command with arguments
     * @param args to use in command
     * @throws InvalidCommandArgumentException if arguments are invalid
     */
    abstract public void execute(String[] args) throws InvalidCommandArgumentException;

    /**
     * @return name of command
     */
    public String getName() {
        return name;
    }

    /**
     * please check {@link clientio.BasicClientIO}
     * @return basic client IO
     */
    public BasicClientIO getIO() {
        return this.io;
    }

    /**
     * please check {@link collectionmanagers.FileCollectionManager}
     * @return file collection manager
     */
    public FileCollectionManager getFileCollectionManager() {
        return this.fcm;
    }

    /**
     * please check {@link commandmanagers.BasicCommandManager}
     * @return basic command manager
     */

    public BasicCommandManager getBasicCommandManager() {
        return bcm;
    }

    /**
     *
     * @return usage of command
     */
    abstract public String getUsage();

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
