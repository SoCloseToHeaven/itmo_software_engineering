package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exception.ExecutingScriptException;
import com.soclosetoheaven.common.exception.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Messages;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashSet;

public class ExecuteScriptCommand extends AbstractCommand{

    public static final String NAME = "execute_script";
    private static final HashSet<File> OPENED_FILES = new HashSet<>();
    private final BasicIO io;

    public ExecuteScriptCommand(BasicIO io) {
        super(NAME);
        this.io = io;
    }


    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length < MIN_ARGS_SIZE)
            throw new InvalidCommandArgumentException();
        Path file = Path.of(args[FIRST_ARG]);
        if (OPENED_FILES.contains(file.toFile()))
            throw new ExecutingScriptException(Messages.EXECUTING_SCRIPT_RECURSION.key);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(URLDecoder.decode(args[0], StandardCharsets.UTF_8))) {
                @Override
                public void close() throws IOException {
                    super.close();
                    ExecuteScriptCommand.OPENED_FILES.remove(file.toFile());
                }
            };
            OPENED_FILES.add(file.toFile());
            io.add(reader);
        } catch (IOException e) {
            throw new ExecutingScriptException(Messages.EXECUTING_SCRIPT_ERROR.key);
        }
        return null;
    }

    @Override
    public String getUsage() {
        return "runs script from your file.";
    }
}
