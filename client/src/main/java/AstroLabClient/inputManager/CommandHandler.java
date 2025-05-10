package AstroLabClient.inputManager;

import AstroLab.actions.components.Action;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.clientCommand.*;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandHandler {
    private Map<String, ClientCommand> commandList = new HashMap<>();

    public CommandHandler(ScannerManager scannerManager, ArgumentRequester argumentRequester) {
        commandList.put("info", new ClientInfo());
        commandList.put("show", new ClientShow());
        commandList.put("clear", new ClientClear());
        commandList.put("help", new ClientHelp());
        commandList.put("print_field_descending_distance", new ClientPrintFieldDescendingDistance());

        commandList.put("count_by_distance", new ClientCountByDistance());
        commandList.put("remove_by_id", new ClientRemoveById());
        commandList.put("execute_script", new ClientExecuteScript(scannerManager));

        commandList.put("add", new ClientAdd(argumentRequester));
        commandList.put("update", new ClientUpdate(argumentRequester));
        commandList.put("add_if_max", new ClientAddIfMax(argumentRequester));
        commandList.put("add_if_min", new ClientAddIfMin(argumentRequester));
        commandList.put("remove_greater", new ClientRemoveGreater(argumentRequester));
        commandList.put("count_greater_than_distance", new ClientCountGreaterThanDistance());

        commandList.put("exit", new ClientExit());
    }

    public Action handle(String commandLine) throws IllegalArgumentException, SystemInClosedException {
        CommandArgumentList arguments = CommandAndArgumentsParser.parseCommandAndArguments(commandLine);
        Action toServerCommandArgumentList;
        String command = (String) arguments.getCommand().getValue();

        if (!commandList.containsKey(command) || command == null) {
            System.out.println("Unexpected command: '" + command + "'. Try write 'help'");
            return null;
        }
        ClientCommand clientCommand = commandList.get(command);

        try {
            toServerCommandArgumentList = clientCommand.input(arguments);
        } catch (SystemInClosedException | ScannerException e) {
            System.out.println("System.in|Console.Scanner closed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return null;
        }
        return toServerCommandArgumentList;
    }
}
