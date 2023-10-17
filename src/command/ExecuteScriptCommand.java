package command;

import clientio.BasicClientIO;
import collectionmanagers.FileCollectionManager;
import commandmanagers.BasicCommandManager;
import exceptions.ExecutingScriptException;
import exceptions.InvalidCommandArgumentException;
import exceptions.InvalidFieldValueException;
import exceptions.UnknownCommandException;
import util.TerminalColors;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class ExecuteScriptCommand extends AbstractCommand {
    /**
     * Opened files set to avoid recursion
     */

    private static final HashSet<File> OPENED_FILES = new HashSet<>();

    public ExecuteScriptCommand(BasicClientIO io, FileCollectionManager fcm, BasicCommandManager bcm) {
        super("execute_script", io, fcm, bcm);
    }

    /**
     *
     * @param args to use in command
     * @throws InvalidCommandArgumentException if arguments are invalid
     * @throws ExecutingScriptException if file is already opened
     */
    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 2)
            throw new InvalidCommandArgumentException(this.getName());
        File file = new File(args[1]);
        if (OPENED_FILES.contains(file))
            throw new ExecutingScriptException("{execute_script} is unable in opened file to avoid recursion");
        LocalScriptManager lsm = new LocalScriptManager(file);
        lsm.run();
    }
    @Override
    public String getUsage() {
        return "%s%s".formatted(
                TerminalColors.setColor("execute_script {filepath}", TerminalColors.GREEN),
                TerminalColors.setColor(" - runs script from your file.",
                        TerminalColors.BLUE));
    }

    /**
     * private inner class to provide more proper work with executing other commands
     */
    private class LocalScriptManager {

        private final File file;

        /**
         * please check {@link commandmanagers.BasicCommandManager}
         */

        private final BasicCommandManager bcm = getBasicCommandManager();
        /**
         * please check {@link clientio.BasicClientIO}
         */

        private final BasicClientIO io = getIO();
        /**
         * data from file
         */
        private final StringBuilder fileData = new StringBuilder();

        /**
         * @param file to read from
         */
        LocalScriptManager(File file) {
            this.file = file;
        }

        /**
         * reads data from file and adds it to {@link #fileData}
         * @return true if successfully read data from file, false if else
         */
        private boolean readFromFile() {
            try {
                io.add(new BufferedReader(new InputStreamReader(new FileInputStream(file))){
                    @Override
                    public void close() throws IOException {
                        super.close();
                        ExecuteScriptCommand.OPENED_FILES.remove(file);
                    }
                });
                fileData.append(io.read());
                return true;
            } catch (IOException e) {
                System.err.printf("%s: %s%n", "Something went wrong while reading file", e.getMessage());
                return false;
            }
        }

        /**
         * @return array of commands and their arguments from file
         */
        private String[] getArgs() {
            return this.fileData.toString().split("\n");
        }

        /**
         * runs local script manager, that executes commands
         */
        public void run() {
            if (!this.readFromFile()) return;
            OPENED_FILES.add(file);
            String[] args = getArgs();
            Arrays.stream(args).forEach(arguments -> {
                try {
                    bcm.manage(arguments);
                } catch (UnknownCommandException |
                         InvalidFieldValueException |
                         InvalidCommandArgumentException |
                         NullPointerException |
                         NumberFormatException |
                         NoSuchElementException |
                         UnsupportedOperationException e) {
                    io.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
                }
            });
        }
    }
}
