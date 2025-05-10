package AstroLabClient.clientAction;

import AstroLab.actions.components.ClientServerAction;

public interface CommandVisitor {
    void visitIt(ClientServerAction clientServerAction) throws Exception;

    void visitIt(ClientAction clientAction) throws Exception;
}
