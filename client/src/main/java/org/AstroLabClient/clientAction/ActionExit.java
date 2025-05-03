package org.AstroLabClient.clientAction;

import org.AstroLab.actions.utils.ActionsName;

import java.rmi.ServerException;

public class ActionExit extends ClientAction{
    public ActionExit(){
        this.actionName = ActionsName.EXIT;
    }

    @Override
    public void executeLocally() throws ServerException {
        throw new ServerException("Exit");
    }
}
