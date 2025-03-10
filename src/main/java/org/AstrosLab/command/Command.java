package org.AstrosLab.command;
import org.AstrosLab.inputManager.CommandArgumentList;

import java.util.ArrayList;


//Потом сделаю наследование для serverCommand и clientCommand, чтобы в одной делать .execute(), а в другой .input()
//????
//    public abstract ArrayList<String> input(String strCommandInLine) throws Exception;

public abstract class Command {
    public abstract String execute(CommandArgumentList args) throws Exception;
    public abstract String description();
    public abstract CommandArgumentList input();
}
