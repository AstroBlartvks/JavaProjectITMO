package org.AstrosLab.command.ServerCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;

import java.util.HashMap;

@Getter
@Setter
public class CommandManager {
    private HashMap<String, ServerCommand> commandList = new HashMap<String, ServerCommand>();

    public CommandManager(CustomCollection collection){
        commandList.put("info", new ServerInfo(collection));
    }

    public String executeComand(CommandArgumentList commandArgList) throws Exception {
        String commandName = commandArgList.getCommandName();
        ServerCommand servCommand = commandList.get(commandName);

        if (servCommand == null){
            throw new Exception("Unexpected command: '"+commandName+"'!");
        }

        return servCommand.execute(commandArgList);
    }
}
