package org.AstrosLab.command.ServerCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommandManager {
    private Map<String, ServerCommand> commandList = new HashMap<String, ServerCommand>();

    public CommandManager(CustomCollection collection){
        commandList.put("info", new ServerInfo(collection));
        commandList.put("show", new ServerShow(collection));
        commandList.put("clear", new ServerClear(collection));
        commandList.put("exit", new ServerExit());
        commandList.put("help", new ServerHelp());
        commandList.put("print_field_descending_distance", new ServerPrintFieldDescendingDistance(collection));
        commandList.put("save", new ServerSave(collection));

        commandList.put("count_by_distance", new ServerCountByDistance(collection));
        commandList.put("remove_by_id", new ServerRemoveById(collection));

        commandList.put("add", new ServerAdd(collection));
        commandList.put("update", new ServerUpdate(collection));
        commandList.put("add_if_max", new ServerAddIfMax(collection));
        commandList.put("add_if_min", new ServerAddIfMin(collection));
        commandList.put("remove_greater", new ServerRemoveGreater(collection));
        commandList.put("count_greater_than_distance", new ServerCountGreaterThanDistance(collection));
    }

    public ServerResponse executeComand(CommandArgumentList commandArgList) throws Exception {
        ServerCommand serverCommand = commandList.get(commandArgList.getCommand().toString());

        if (serverCommand == null){
            throw new Exception("Unexpected command: '"+commandArgList.getCommand().toString()+"'!");
        }

        return serverCommand.execute(commandArgList);
    }
}
