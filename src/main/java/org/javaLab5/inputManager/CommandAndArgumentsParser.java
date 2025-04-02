package org.javaLab5.inputManager;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;

public class CommandAndArgumentsParser {
    public static CommandArgumentList parseCommandAndArguments(String commandLine) {
        CommandArgumentList argList = new CommandArgumentList();
        String[] stringArgs = commandLine.split("\\s+", 2);
        argList.addArgument(new CommandArgument(stringArgs[0]));
        //Да, решение объективно не очень, здесь всегда будет 1 параметр, что не масштабируемо,
        //но если нужно будет больше - подумаю о том, чтобы создать DTO для команды и его передавать, а пока будет так
        argList.addArgument(stringArgs.length > 1 ? new CommandArgument(stringArgs[1]) : new CommandArgument(null));
        return argList;
    }
}
