package org.javaLab5.command.clientCommand;

import lombok.Getter;
import lombok.Setter;
import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class CommandIdentifier {
    private Map<String, ClientCommand> commandList = new HashMap<>();

    public CommandIdentifier(){
        commandList.put("info", new ClientInfo());
        commandList.put("show", new ClientShow());
        commandList.put("clear", new ClientClear());
        commandList.put("exit", new ClientExit());
        commandList.put("help", new ClientHelp());
        commandList.put("print_field_descending_distance", new ClientPrintFieldDescendingDistance());
        commandList.put("save", new ClientSave());

        commandList.put("count_by_distance", new ClientCountByDistance());
        commandList.put("remove_by_id", new ClientRemoveById());
        commandList.put("execute_script", new ClientExecuteScript());

        commandList.put("add", new ClientAdd());
        commandList.put("update", new ClientUpdate());
        commandList.put("add_if_max", new ClientAddIfMax());
        commandList.put("add_if_min", new ClientAddIfMin());
        commandList.put("remove_greater", new ClientRemoveGreater());
        commandList.put("count_greater_than_distance", new ClientCountGreaterThanDistance());

    }

    public ClientCommand getCommand(String commandLine) throws Exception{
        CommandArgumentList args = parseCommand(commandLine);
        return commandList.get(args.getCommand().getValue().toString());
    }

    public static CommandArgumentList parseCommand(String commandLine) throws Exception {
        CommandArgumentList argList = new CommandArgumentList();
        Pattern pattern = Pattern.compile("^(\\w+)(?:\\s+(\\S+))?$");
        Matcher matcher = pattern.matcher(commandLine.trim());

        if (matcher.matches()) {
            String command = matcher.group(1);
            argList.addArgument(new CommandArgument(command.toLowerCase()));

            if (matcher.group(2) != null) {
                String argument = matcher.group(2);

                argList.addArgument(new CommandArgument(argument));
            } else {
                argList.addArgument(new CommandArgument(null));
            }
        } else {
            throw new Exception("Error: the command '" + commandLine + "' does not match the format: 'command' 'argumentInLine' | 'command' #noArguments | 'command' {aLotOfArguments]");
        }
        if (argList.length() < 1 || argList.length() > 2){
            throw new Exception("Error: the command '" + commandLine + "' does not match the format: 'command' 'argumentInLine' | 'command' #noArguments | 'command' {aLotOfArguments]");
        }

        return argList;
    }
}
