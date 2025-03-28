package org.javaLab5.inputManager;

import lombok.Getter;
import lombok.Setter;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.command.clientCommand.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class CommandIdentifier {
    private Map<String, ClientCommand> commandList = new HashMap<>();

    public CommandIdentifier(ScannerManager scannerManager, ArgumentRequester argumentRequester){
        commandList.put("info", new ClientInfo());
        commandList.put("show", new ClientShow());
        commandList.put("clear", new ClientClear());
        commandList.put("exit", new ClientExit());
        commandList.put("help", new ClientHelp());
        commandList.put("print_field_descending_distance", new ClientPrintFieldDescendingDistance());
        commandList.put("save", new ClientSave());

        commandList.put("count_by_distance", new ClientCountByDistance());
        commandList.put("remove_by_id", new ClientRemoveById());
        commandList.put("execute_script", new ClientExecuteScript(scannerManager));

        commandList.put("add", new ClientAdd(argumentRequester));
        commandList.put("update", new ClientUpdate(argumentRequester));
        commandList.put("add_if_max", new ClientAddIfMax(argumentRequester));
        commandList.put("add_if_min", new ClientAddIfMin(argumentRequester));
        commandList.put("remove_greater", new ClientRemoveGreater(argumentRequester));
        commandList.put("count_greater_than_distance", new ClientCountGreaterThanDistance());

    }

    public ClientCommand getCommand(String commandLine) throws UndefinedCommandException {
        CommandArgumentList args = CommandParser.parseCommand(commandLine);
        String command = args.getCommand().getValue().toString();
        if (!commandList.containsKey(command)){
            throw new UndefinedCommandException("Unexpected command: '" + command + "'. Try write 'help'");
        }
        ClientCommand clientCommand = commandList.get(command);
        clientCommand.setArgumentList(args);
        return clientCommand;
    }

}
