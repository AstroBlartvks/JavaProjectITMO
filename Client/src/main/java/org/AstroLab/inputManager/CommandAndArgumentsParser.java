package org.AstroLab.inputManager;

import org.AstroLab.utils.command.CommandArgument;
import org.AstroLab.utils.command.CommandArgumentList;

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
