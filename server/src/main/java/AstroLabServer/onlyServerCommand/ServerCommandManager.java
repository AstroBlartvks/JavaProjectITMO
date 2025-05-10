package AstroLabServer.onlyServerCommand;

import AstroLabServer.collection.CustomCollection;
import java.util.HashMap;
import java.util.Map;

public class ServerCommandManager {
    private final Map<String, OnlyServerCommand> commandList = new HashMap<>();

    public ServerCommandManager(CustomCollection collection) {
        commandList.put("save", new OnlyServerSave(collection));
        commandList.put("exit", new OnlyServerExit());
    }

    public OnlyServerResult executeCommand(String commandName) throws Exception {
        OnlyServerCommand onlyServerCommand = commandList.get(commandName);
        if (onlyServerCommand == null) {
            throw new Exception("Unexpected command: '" + commandName + "'!");
        }
        return onlyServerCommand.execute();
    }
}
