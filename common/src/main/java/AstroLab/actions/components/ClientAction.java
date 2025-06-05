package AstroLab.actions.components;

import AstroLab.actions.utils.ActionVisitable;

public abstract class ClientAction extends Action {
    public abstract void executeLocally() throws Exception;

    @Override
    public void accept(ActionVisitable visitor) throws Exception {
        visitor.visit(this);
    }
}
