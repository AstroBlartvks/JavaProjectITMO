package AstroLab.actions.utils;

import AstroLab.actions.components.ClientAction;
import AstroLab.actions.components.ClientServerAction;

public interface ActionVisitable {
    void visit(ClientAction clientAction) throws Exception;
    void visit(ClientServerAction clientServerAction) throws Exception;
}
