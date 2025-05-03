package org.AstroLabClient.clientAction;

import org.AstroLab.actions.components.ClientServerAction;

public interface CommandVisitor {
    public void visit(ClientServerAction clientServerAction) throws Exception;
    public void visit(ClientAction clientAction) throws Exception;
}
