package AstroLabClient.clientAction;

import AstroLab.actions.components.ClientAction;
import AstroLab.actions.utils.ActionsName;
import AstroLab.grpc.ActionsNameEnum;

import java.rmi.ServerException;

public final class ActionExit extends ClientAction {
    /**.
     * Create Action
     */
    public ActionExit() {
        this.setActionName(ActionsNameEnum.EXIT);
    }

    @Override
    public void executeLocally() throws ServerException {
        throw new ServerException("Exit");
    }
}
