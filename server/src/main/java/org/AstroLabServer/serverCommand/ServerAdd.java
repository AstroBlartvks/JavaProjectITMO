package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionAdd;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.model.Route;
import org.AstroLabServer.collection.CustomCollection;

import java.util.Date;

public class ServerAdd extends ServerCommand{
    private final CustomCollection collection;

    public ServerAdd(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAdd action = (ActionAdd) args;
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDTO());
        collection.addElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully added ");
    }

}
