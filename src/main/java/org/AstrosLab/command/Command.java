package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

//CNA - command no arguments
//CIL - command in line
//CMA - Command more arguments

public abstract class Command {
    customCollection collection;
    Exception error;

    public abstract String execute(String commandText);
    public abstract String description();
    public Exception getException(){
        return this.error;
    }
}
