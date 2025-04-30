package org.AstroLabServer.serverCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.utils.TypesOfActions;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLabServer.collection.CustomCollection;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommandManager {
    private Map<TypesOfActions, ServerCommand> commandList = new HashMap<>();

    public CommandManager(CustomCollection collection){
        commandList.put(TypesOfActions.INFO, new ServerInfo(collection));
        commandList.put(TypesOfActions.SHOW, new ServerShow(collection));
        commandList.put(TypesOfActions.CLEAR, new ServerClear(collection));
        commandList.put(TypesOfActions.HELP, new ServerHelp());
        commandList.put(TypesOfActions.PRINT_FIELD_DESCENDING_DISTANCE, new ServerPrintFieldDescendingDistance(collection));

        commandList.put(TypesOfActions.COUNT_BY_DISTANCE, new ServerCountByDistance(collection));
        commandList.put(TypesOfActions.REMOVE_BY_ID, new ServerRemoveById(collection));

        commandList.put(TypesOfActions.ADD, new ServerAdd(collection));
        commandList.put(TypesOfActions.UPDATE, new ServerUpdate(collection));
        commandList.put(TypesOfActions.ADD_IF_MAX, new ServerAddIfMax(collection));
        commandList.put(TypesOfActions.ADD_IF_MIN, new ServerAddIfMin(collection));
        commandList.put(TypesOfActions.REMOVE_GREATER, new ServerRemoveGreater(collection));
        commandList.put(TypesOfActions.COUNT_GREATER_THAN_DISTANCE, new ServerCountGreaterThanDistance(collection));
    }

    public ServerResponse executeCommand(Action action) throws Exception {
        ServerCommand serverCommand = commandList.get(action.getActionType());

        if (serverCommand == null){
            throw new Exception("Unexpected command: '"+action.getActionType()+"'!");
        }

        return serverCommand.execute(action);
    }
    
}
