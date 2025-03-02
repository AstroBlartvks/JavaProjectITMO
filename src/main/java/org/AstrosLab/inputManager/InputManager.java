package org.AstrosLab.inputManager;

import org.AstrosLab.command.Command;
import org.AstrosLab.command.UnexpectedCommandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class InputManager {
    protected HashMap<String, Command> commandListing;
    private Exception error;

    public InputManager(HashMap<String, Command> commandListing){
        this.commandListing = commandListing;
    }

    public ArrayList<String> input(){
        String[] commandAndArgument;
        Scanner in = new Scanner(System.in);

        System.out.print("Write command: \n>>> ");
        String strCommandInLine = in.nextLine().trim();

        try {
            commandAndArgument = strCommandInLine.split(" ");
        } catch (PatternSyntaxException e){
            this.error = e;
            return null;
        }

        if (commandAndArgument.length == 0 || strCommandInLine.contains("\\n")){
            this.error = new UnexpectedCommandException("You have entered an cursed command: '"+strCommandInLine+"', dont use \\n");
            return null;
        }

        String StrCommand = commandAndArgument[0];

        if (!this.commandListing.containsKey(StrCommand)){
            this.error = new UnexpectedCommandException("You have entered an unknown command: '" + StrCommand + "'.\nParsed from string: '"+strCommandInLine+"'");
            return null;
        }

        Command cmd = this.commandListing.get(StrCommand);
        try {
            ArrayList<String> allCommand = cmd.input(strCommandInLine);
            return allCommand;
        } catch (Exception e){
            this.error = e;
            return null;
        }
    }

    public Exception getException() {
        return this.error;
    }
}
