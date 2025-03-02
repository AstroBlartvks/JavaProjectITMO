package org.AstrosLab.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

public class CommandManager {
    protected HashMap<String, Command> commandListing;
    private Exception error;

    public CommandManager(HashMap<String, Command> commandListing){
        this.commandListing = commandListing;
    }

    public String exec(ArrayList<String> StrCommandLine){
        String[] commandAndArgument;

        try {
            commandAndArgument = StrCommandLine.get(0).split(" ");
        } catch (PatternSyntaxException e){
            this.error = e;
            return "";
        }
        String StrCommand = commandAndArgument[0];

        Command cmd = this.commandListing.get(StrCommand);
        if (cmd == null){
            this.error = new UnexpectedCommandException("You have entered an unknown command: '" + StrCommand + "'.\nParsed from string: '"+StrCommandLine+"'");
            return "";
        }

        try {
            return cmd.execute(StrCommandLine);
        } catch (Exception e) {
            this.error = e;
            return "";
        }
    }


    public Exception getException() {
        return this.error;
    }
}
