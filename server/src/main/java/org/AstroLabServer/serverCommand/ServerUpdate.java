package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionUpdate;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.model.Route;
import org.AstroLabServer.collection.CustomCollection;

import java.util.Date;

public class ServerUpdate extends ServerCommand{
    private final CustomCollection collection;

    public ServerUpdate(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) throws IllegalArgumentException {
        ActionUpdate action = (ActionUpdate) args;

        if (!this.collection.containsID(action.getId())){
            throw new IllegalArgumentException("There is no such 'Route' with 'id'=" + action.getId());
        }

        Route newRoute = new Route();

        newRoute.setId(action.getId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDTO());

        this.collection.updateElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully updated");
    }
}
