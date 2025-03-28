package org.javaLab5;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.javaLab5.collection.CustomCollection;

import org.javaLab5.command.clientCommand.ClientCommand;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.inputManager.CommandIdentifier;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.command.clientCommand.UndefinedCommandException;
import org.javaLab5.command.serverCommand.CommandManager;
import org.javaLab5.command.serverCommand.ResponseStatus;
import org.javaLab5.command.serverCommand.ServerResponse;
import org.javaLab5.files.JsonReader;
import org.javaLab5.files.Reader;


import org.javaLab5.inputManager.ScannerManager;
//execute_script src/resources/scripts/script1.sc - пример рабоыт программы
//execute_script src/resources/scripts/rcs1.sc - рекурсия
//execute_script src/resources/scripts/main.sc - много скриптов


//Pray to god that this works
public class Main {
    public static void main(String[] args) {
        CustomCollection collection = new CustomCollection();

        Reader reader = new Reader(new JsonReader());
        try {
            collection = reader.readFromEnv();
        } catch (JsonMappingException e) {
            System.err.println("Program can't parse your Json file, check this error and try fix it!\n\t" + e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("Ops... Exception while reading!\n" + e.getMessage());
            System.exit(-1);
        }

        ScannerManager scannerManager = new ScannerManager();
        CommandManager commandManager = new CommandManager(collection);
        ArgumentRequester argumentRequester = new ArgumentRequester(scannerManager);
        CommandIdentifier commandIndent = new CommandIdentifier(scannerManager, argumentRequester);

        while (scannerManager.hasNextLine()) {
            //User
            String input = scannerManager.readLine();
            ClientCommand clientCommand;
            CommandArgumentList commandArgList;

            try {
                clientCommand = commandIndent.getCommand(input);
            } catch (UndefinedCommandException e) {
                System.out.println("Exception: " + e.getMessage());
                continue;
            }

            if (clientCommand == null) {
                System.out.println("Unexpected command: '" + input + "'. Try write 'help'");
                continue;
            }

            try {
                commandArgList = clientCommand.input();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                continue;
            }

            if (commandArgList == null){
                continue;
            }

            //Server
            try {
                ServerResponse response = commandManager.executeCommand(commandArgList);
                if (response.getStatus() == ResponseStatus.EXIT){
                    return;
                }
                System.out.println(response);
            } catch (Exception e) {
                ServerResponse response = new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
                System.out.println(response);
            }
        }
    }
}
