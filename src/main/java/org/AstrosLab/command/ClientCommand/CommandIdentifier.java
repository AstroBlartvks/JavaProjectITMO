package org.AstrosLab.command.ClientCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstrosLab.command.CommandArgument;
import org.AstrosLab.command.CommandArgumentList;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class CommandIdentifier {
    private Map<String, ClientCommand> commandList = new HashMap<String, ClientCommand>();

    public CommandIdentifier(){
        commandList.put("info", new ClientInfo());
        commandList.put("show", new ClientShow());
        commandList.put("count_by_distance", new ClientCountByDistance());
        commandList.put("add", new ClientAdd());
    }

    public ClientCommand getCommand(String commandLine) throws Exception{
        CommandArgumentList args = parseCommand(commandLine);
        if (args != null) {
            return commandList.get(args.getCommand().getValue());
        }
        else{
            return null;
        }
    }

    public static CommandArgumentList parseCommand(String commandLine) {
        CommandArgumentList argList = new CommandArgumentList();
        Pattern pattern = Pattern.compile("^(\\w+)(?:\\s+(\\S+))?$");
        Matcher matcher = pattern.matcher(commandLine.trim());

        if (matcher.matches()) {
            String command = matcher.group(1);
            argList.addArgument(new CommandArgument(command));

            if (matcher.group(2) != null) {
                String argument = matcher.group(2);

                argList.addArgument(new CommandArgument(argument));
            } else {
                argList.addArgument(new CommandArgument(null));
            }
        } else {
            System.out.println("Error: the command '" + commandLine + "' does not match the format: 'command' 'argumentInLine' | 'command' #noArguments | 'command' {aLotOfArguments]");
            return null;
        }

        return argList;
    }
}
