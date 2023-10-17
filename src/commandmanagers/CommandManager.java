package commandmanagers;

import command.AbstractCommand;
import exceptions.InvalidCommandArgumentException;
import exceptions.UnknownCommandException;
import util.LRUCache;

import java.util.Map;

public interface CommandManager {
    /**
     * receives command's name and executes command with received name and arguments
     * @param commandString received name and arguments
     * @throws UnknownCommandException if there is no such command with received name
     * @throws InvalidCommandArgumentException if command arguments are invalid
     */
    void manage(String commandString) throws UnknownCommandException, InvalidCommandArgumentException;

    /**
     * please check {@link command.AbstractCommand}
     * @return Map with all commands
     */
    Map<String, ? extends AbstractCommand> getCommands();

    /**
     * adds new command to command manager, please check {@link command.AbstractCommand}
     * @param command to add
     */
    void addCommand(AbstractCommand command);

    /**
     * please check {@link util.LRUCache}, {@link command.AbstractCommand}
     * @return collection with command history
     */

    LRUCache<AbstractCommand> getCommandHistory();
}
