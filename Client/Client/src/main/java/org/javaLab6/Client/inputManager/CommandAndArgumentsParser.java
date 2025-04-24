package org.javaLab6.Client.inputManager;

import org.javaLab6.Client.utils.command.CommandArgument;
import org.javaLab6.Client.utils.command.CommandArgumentList;

public class CommandAndArgumentsParser {
    public static CommandArgumentList parseCommandAndArguments(String commandLine) {
        CommandArgumentList argList = new CommandArgumentList();
        String[] stringArgs = commandLine.split("\\s+", 2);
        argList.addArgument(new CommandArgument(stringArgs[0]));
        if (stringArgs.length > 1) {
            argList.addArgument(new CommandArgument(stringArgs[1]));
        }
        return argList;
    }
}
