package org.AstrosLab;

import org.AstrosLab.collection.CustomCollection;

import org.AstrosLab.command.ClientCommand.ClientCommand;
import org.AstrosLab.command.ClientCommand.CommandIdentifier;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.command.ServerCommand.CommandManager;
import org.AstrosLab.files.JsonReader;
import org.AstrosLab.files.Reader;
import org.AstrosLab.inputManager.InputManager;

import java.util.HashMap;


//Pray to god that this works
public class Main {
    public static void main(String[] args) {
        CustomCollection collection = new CustomCollection();

        Reader read = new Reader(new JsonReader());
        try {
            collection = read.readFromEnv("JAVATESTFILE");
        } catch (Exception e) {
            System.out.println("Ops... Programm got some exception(s)!\n" + e);
            return;
        }

        //User
        CommandManager commandManager = new CommandManager(collection);

        //Program
        InputManager inputManager = new InputManager();
        CommandIdentifier comandIndent = new CommandIdentifier();


        while (inputManager.hasNextInput()){
            //User
            String inputCommand = inputManager.input();
            ClientCommand clientCommand = comandIndent.getCommand(inputCommand);

            if (clientCommand == null) {
                System.out.println("Unexpected commnad: '"+inputCommand+"'. Try write 'help'");
                continue;
            }
            CommandArgumentList commandArgList = clientCommand.input();

            //Program
            try {
                String response = commandManager.executeComand(commandArgList);
                System.out.println("Results:\n" + response);
            } catch (Exception e){
                System.out.println("Exception in command:\n"+e+"\n");
            }
        }
    }
}
