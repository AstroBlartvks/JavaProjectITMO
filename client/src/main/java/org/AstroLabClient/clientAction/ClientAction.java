package org.AstroLabClient.clientAction;

import org.AstroLab.actions.components.Action;

public abstract class ClientAction extends Action {
    public abstract void executeLocally() throws Exception;
}
