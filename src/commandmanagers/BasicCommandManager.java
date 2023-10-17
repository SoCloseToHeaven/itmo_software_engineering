package commandmanagers;

import command.*;
import exceptions.InvalidCommandArgumentException;
import exceptions.InvalidFieldValueException;
import exceptions.UnknownCommandException;
import util.LRUCache;

import java.util.HashMap;
import java.util.Map;

/**
 * basic command invoker
 */
public class BasicCommandManager implements CommandManager{

    /**
     * All usable commands, please see {@link command.AbstractCommand}
     */
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();

    /**
     * maximum history of used commands size
     */

    private static final int MAX_HISTORY_SIZE = 13;

    /**
     * used commands history, please see {@link util.LRUCache} and {@link command.AbstractCommand\}
     */
    private final LRUCache<AbstractCommand> commandHistory = new LRUCache<>(MAX_HISTORY_SIZE) {
        @Override
        public String toString() {
            StringBuilder commandHistoryString = new StringBuilder();
            this.getArray().forEach(command -> commandHistoryString.append("%s ".formatted(command.getName())));
            return commandHistoryString.toString();
        }
    };

    @Override
    public void manage(String commandString)
            throws UnknownCommandException, InvalidCommandArgumentException, InvalidFieldValueException {
        String[] args = commandString.strip().split("\\s+");
        args[0] = args[0].toLowerCase(); // case sensitivity
        if (commands.get(args[0]) == null)
            throw new UnknownCommandException("%s - %s".formatted(args[0], "Type {help} to get all usable commands."));
        commands.get(args[0]).execute(args);
        commandHistory.add(commands.get(args[0]));
    }

    @Override
    public void addCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }

    @Override
    public LRUCache<AbstractCommand> getCommandHistory() {
        return this.commandHistory;
    }

    @Override
    public Map<String, ? extends AbstractCommand> getCommands() {
        return commands;
    }
}
