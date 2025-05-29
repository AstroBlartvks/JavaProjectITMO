package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import AstroLabServer.database.RouteDAO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandManager {
    private Map<ActionsName, ServerCommand> commandList = new HashMap<>();
    private final Connection connection;

    public CommandManager(CustomCollection collection, Connection connection) {
        this.connection = connection;
        RouteDAO newRouteDAO = new RouteDAO(this.connection);

        commandList.put(ActionsName.INFO, new ServerInfo(collection));
        commandList.put(ActionsName.SHOW, new ServerShow(collection));
        commandList.put(ActionsName.CLEAR, new ServerClear(collection));
        commandList.put(ActionsName.HELP, new ServerHelp());
        commandList.put(ActionsName.PRINT_FIELD_DESCENDING_DISTANCE,
                new ServerPrintFieldDescendingDistance(collection));

        commandList.put(ActionsName.COUNT_BY_DISTANCE, new ServerCountByDistance(collection));
        commandList.put(ActionsName.REMOVE_BY_ID, new ServerRemoveById(collection, newRouteDAO));

        commandList.put(ActionsName.ADD, new ServerAdd(collection, newRouteDAO));
        commandList.put(ActionsName.UPDATE, new ServerUpdate(collection, newRouteDAO));
        commandList.put(ActionsName.ADD_IF_MAX, new ServerAddIfMax(collection, newRouteDAO));
        commandList.put(ActionsName.ADD_IF_MIN, new ServerAddIfMin(collection, newRouteDAO));
        commandList.put(ActionsName.REMOVE_GREATER, new ServerRemoveGreater(collection, newRouteDAO));
        commandList.put(ActionsName.COUNT_GREATER_THAN_DISTANCE,
                new ServerCountGreaterThanDistance(collection));
    }

    public ServerResponse executeCommand(Action action) throws Exception {
        ServerCommand serverCommand = commandList.get(action.getActionName());

        if (serverCommand == null) {
            throw new Exception("Unexpected command: '" + action.getActionName() + "'!");
        }

        return serverCommand.execute(action);
    }
}
