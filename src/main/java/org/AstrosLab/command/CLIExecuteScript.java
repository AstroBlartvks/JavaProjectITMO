package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CLIExecuteScript extends Command{
    public CLIExecuteScript(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "execute_script filename: read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user interactively.\n";
    }
}

