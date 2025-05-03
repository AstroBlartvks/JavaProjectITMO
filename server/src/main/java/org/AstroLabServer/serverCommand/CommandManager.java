package org.AstroLabServer.serverCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.utils.ActionsName;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLabServer.collection.CustomCollection;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommandManager {
    private Map<ActionsName, ServerCommand> commandList = new HashMap<>();

    public CommandManager(CustomCollection collection){
        commandList.put(ActionsName.INFO, new ServerInfo(collection));
        commandList.put(ActionsName.SHOW, new ServerShow(collection));
        commandList.put(ActionsName.CLEAR, new ServerClear(collection));
        commandList.put(ActionsName.HELP, new ServerHelp());
        commandList.put(ActionsName.PRINT_FIELD_DESCENDING_DISTANCE, new ServerPrintFieldDescendingDistance(collection));

        commandList.put(ActionsName.COUNT_BY_DISTANCE, new ServerCountByDistance(collection));
        commandList.put(ActionsName.REMOVE_BY_ID, new ServerRemoveById(collection));

        commandList.put(ActionsName.ADD, new ServerAdd(collection));
        commandList.put(ActionsName.UPDATE, new ServerUpdate(collection));
        commandList.put(ActionsName.ADD_IF_MAX, new ServerAddIfMax(collection));
        commandList.put(ActionsName.ADD_IF_MIN, new ServerAddIfMin(collection));
        commandList.put(ActionsName.REMOVE_GREATER, new ServerRemoveGreater(collection));
        commandList.put(ActionsName.COUNT_GREATER_THAN_DISTANCE, new ServerCountGreaterThanDistance(collection));
    }

    public ServerResponse executeCommand(Action action) throws Exception {
        ServerCommand serverCommand = commandList.get(action.getActionName());

        if (serverCommand == null){
            throw new Exception("Unexpected command: '"+action.getActionName()+"'!");
        }

        return serverCommand.execute(action);
    }
    
}
