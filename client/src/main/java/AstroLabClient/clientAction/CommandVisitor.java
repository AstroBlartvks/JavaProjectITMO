package AstroLabClient.clientAction;

import AstroLab.actions.components.ClientServerAction;

public interface CommandVisitor {
    void visit(ClientServerAction clientServerAction) throws Exception;

    void visit(ClientAction clientAction) throws Exception;
}
