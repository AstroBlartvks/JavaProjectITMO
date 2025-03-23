package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;

public class CommandParser {
    public static CommandArgumentList parseCommand(String commandLine) {
        CommandArgumentList argList = new CommandArgumentList();
        String[] stringArgs = commandLine.split("\\s+", 2);
        argList.addArgument(new CommandArgument(stringArgs[0]));
        argList.addArgument(stringArgs.length > 1 ? new CommandArgument(stringArgs[1]) : new CommandArgument(null));
        return argList;
    }
}
