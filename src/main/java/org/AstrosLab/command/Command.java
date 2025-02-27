package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public abstract class Command {
    customCollection collection;

    public abstract String execute(String commandText);
    public abstract String description();
}
