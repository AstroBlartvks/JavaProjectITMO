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
        commandList.put("count_by_distance", new ServerCountByDistance(collection));
        commandList.put("add", new ServerAdd(collection));
    }

    public String executeComand(CommandArgumentList commandArgList) throws Exception {
        ServerCommand serverCommand = commandList.get(commandArgList.getCommand().toString());

        if (serverCommand == null){
            throw new Exception("Unexpected command: '"+commandArgList.getCommand().toString()+"'!");
        }

        return serverCommand.execute(commandArgList);
    }
}
