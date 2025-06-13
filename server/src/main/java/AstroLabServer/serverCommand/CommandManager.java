package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.utils.ActionsName;
import AstroLab.grpc.ActionsNameEnum;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ClientRequest;
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
    private Map<ActionsNameEnum, ServerCommand> commandList = new HashMap<>();
    private final Connection connection;

    public CommandManager(CustomCollection collection, Connection connection) {
        this.connection = connection;
        RouteDAO newRouteDAO = new RouteDAO(this.connection);

        commandList.put(ActionsNameEnum.INFO, new ServerInfo(collection));
        commandList.put(ActionsNameEnum.SHOW, new ServerShow(collection));
        commandList.put(ActionsNameEnum.CLEAR, new ServerClear(collection));
        commandList.put(ActionsNameEnum.HELP, new ServerHelp());
        commandList.put(ActionsNameEnum.PRINT_FIELD_DESCENDING_DISTANCE,
                new ServerPrintFieldDescendingDistance(collection));

        commandList.put(ActionsNameEnum.COUNT_BY_DISTANCE, new ServerCountByDistance(collection));
        commandList.put(ActionsNameEnum.REMOVE_BY_ID, new ServerRemoveById(collection, newRouteDAO));

        commandList.put(ActionsNameEnum.ADD, new ServerAdd(collection, newRouteDAO));
        commandList.put(ActionsNameEnum.UPDATE, new ServerUpdate(collection, newRouteDAO));
        commandList.put(ActionsNameEnum.ADD_IF_MAX, new ServerAddIfMax(collection, newRouteDAO));
        commandList.put(ActionsNameEnum.ADD_IF_MIN, new ServerAddIfMin(collection, newRouteDAO));
        commandList.put(ActionsNameEnum.REMOVE_GREATER, new ServerRemoveGreater(collection, newRouteDAO));
        commandList.put(ActionsNameEnum.COUNT_GREATER_THAN_DISTANCE,
                new ServerCountGreaterThanDistance(collection));
    }

    public ServerResponse executeCommand(ClientServerActionMessage clientServerActionMessage) throws Exception {
        ServerCommand serverCommand = commandList.get(clientServerActionMessage.getActionName());

        if (serverCommand == null) {
            throw new Exception("Unexpected command: '" + clientServerActionMessage.getActionName() + "'!");
        }

        return serverCommand.execute(clientServerActionMessage);
    }
}
