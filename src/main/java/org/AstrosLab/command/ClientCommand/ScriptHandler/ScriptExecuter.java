package org.AstrosLab.command.ClientCommand.ScriptHandler;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.ClientCommand.ClientCommand;
import org.AstrosLab.command.ClientCommand.CommandIdentifier;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.command.ServerCommand.CommandManager;
import org.AstrosLab.command.ServerCommand.ResponseStatus;
import org.AstrosLab.command.ServerCommand.ServerResponse;
import org.AstrosLab.inputManager.InputManager;
import org.AstrosLab.inputManager.ScannerManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;


public class ScriptExecuter {
    private static final Stack<String> scriptStack = new Stack<>();
    //Уберу после разделения на сервер и клиент
    public static CustomCollection collection;


    public void execute(String scriptName) throws Exception{
        //Уберу после разделения на сервер и клиент
        CommandManager commandManager = new CommandManager(collection);

        if (!new File(scriptName).exists()) {
            throw new Exception("Script-file '"+scriptName+"' doesn't exist!");
        }
        if (!Files.isReadable(Paths.get(scriptName))) {
            throw new Exception("Insufficient permissions to read the '"+scriptName+"' script-file");
        }

        if (scriptStack.contains(scriptName)){
            String fileSeq = scriptStack.toString();

            scriptStack.clear();
            ScannerManager.closeFileScanner();
            ScannerManager.setConsoleScanner();
            throw new RecursionDetectedException("Recursion in files detected: " + fileSeq + "!");
        }

        try (Scanner scriptScanner = new Scanner(new File(scriptName))) {
            scriptStack.add(scriptName);

            ScannerManager.setFileScanner(scriptScanner);
            ScannerManager.setMainFileScanner();

            //User
            InputManager inputManager = new InputManager();
            CommandIdentifier commandIndent = new CommandIdentifier();

            while (scriptScanner.hasNextLine()){
                String input = inputManager.input();
                ClientCommand clientCommand;
                CommandArgumentList commandArgList;

                System.out.println(">>> "+input);
                try {
                    clientCommand = commandIndent.getCommand(input);
                } catch (Exception e) {
                    System.out.println("Command: '" + input + "' doesn't exist, check 'help'");
                    continue;
                }

                if (clientCommand == null) {
                    System.out.println("Unexpected command: '" + input + "'. Try write 'help'");
                    continue;
                }

                try {
                    commandArgList = clientCommand.input(input);
                } catch (RecursionDetectedException e){
                    throw e;
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                    continue;
                }

                if (commandArgList == null){
                    continue;
                }

                //Уберу после разделения на сервер и клиент
                try {
                    ServerResponse response = commandManager.executeComand(commandArgList);
                    if (response.getStatus() == ResponseStatus.EXIT){
                        System.exit(0);
                    }
                    System.out.println(response);
                } catch (Exception e) {
                    ServerResponse response = new ServerResponse(ResponseStatus.EXCEPTION, e);
                    System.out.println(response);
                }
            }
            scriptStack.pop();
            ScannerManager.setConsoleScanner();
        } catch (FileNotFoundException exception) {
            throw new Exception("Script-file '"+scriptName+"' doesn't exist!");
        } catch (NoSuchElementException exception) {
            throw new Exception("Script-file '"+scriptName+"' is empty!");
        } catch (IllegalStateException exception) {
            throw new Exception("Unexpected error!");
        }
    }
}
