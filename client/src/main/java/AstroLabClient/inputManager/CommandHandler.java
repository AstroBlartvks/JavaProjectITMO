package AstroLabClient.inputManager;

import AstroLab.actions.components.Action;
import AstroLab.auth.UserDTO;
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
    private UserDTO userDTO;

    public CommandHandler(ScannerManager scannerManager, ArgumentRequester argumentRequester, UserDTO userDTO) {
        this.userDTO = userDTO;

        commandList.put("info", new ClientInfo(userDTO));
        commandList.put("show", new ClientShow(userDTO));
        commandList.put("clear", new ClientClear(userDTO));
        commandList.put("help", new ClientHelp(userDTO));
        commandList.put("print_field_descending_distance", new ClientPrintFieldDescendingDistance(userDTO));

        commandList.put("count_by_distance", new ClientCountByDistance(userDTO));
        commandList.put("remove_by_id", new ClientRemoveById(userDTO));
        commandList.put("execute_script", new ClientExecuteScript(scannerManager, userDTO));

        commandList.put("add", new ClientAdd(argumentRequester, userDTO));
        commandList.put("update", new ClientUpdate(argumentRequester, userDTO));
        commandList.put("add_if_max", new ClientAddIfMax(argumentRequester, userDTO));
        commandList.put("add_if_min", new ClientAddIfMin(argumentRequester, userDTO));
        commandList.put("remove_greater", new ClientRemoveGreater(argumentRequester, userDTO));
        commandList.put("count_greater_than_distance", new ClientCountGreaterThanDistance(userDTO));

        commandList.put("exit", new ClientExit(userDTO));
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
