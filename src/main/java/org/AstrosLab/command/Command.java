package org.AstrosLab.command;

import lombok.Getter;
import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

//CNA - command no arguments
//CIL/CLI - command in line / command line in
//CMA - Command more arguments

//Потом сделаю наследование для serverCommand и clientCommand, чтобы в одной делать .execute(), а в другой .input()
public abstract class Command {
    protected CustomCollection collection;
    protected Exception error;
    @Getter
    protected CMDTypes type;
    protected int rowCount;

    public abstract String execute(ArrayList<String> strCommandInLine);
    public abstract ArrayList<String> input(String strCommandInLine) throws Exception;
    public abstract String description();

    public Exception getException(){
        return this.error;
    }

    protected void clearException(){
        this.error = null;
    }

}
