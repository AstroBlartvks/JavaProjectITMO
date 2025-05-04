package AstroLabClient.clientAction;

import AstroLab.actions.components.Action;

public abstract class ClientAction extends Action {
    public abstract void executeLocally() throws Exception;
}
