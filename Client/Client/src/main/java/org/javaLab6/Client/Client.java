package org.javaLab6.Client;

import org.javaLab6.Client.inputManager.*;
import org.javaLab6.Client.utils.ClientServer.ClientRequest;
import org.javaLab6.Client.utils.ClientServer.ClientStatus;
import org.javaLab6.Client.utils.command.CommandArgumentList;

public final class Client {
    private final ScannerManager scannerManager;
    private final CommandHandler commandHandler;

    public Client(){
        this.scannerManager = new ScannerManager();
        this.commandHandler = new CommandHandler(scannerManager, new ArgumentRequester(scannerManager));
    }

    public void run(){
        while (scannerManager.hasNextLine()) {
            CommandArgumentList commandArgList;

            String inputString = input();
            if (inputString == null) continue;

            try {
                commandArgList = handleCommand(inputString);
            } catch (SystemInClosedException | ScannerException e){
                return;
            }

            ClientRequest clientRequest = createRequest(commandArgList);
            System.out.println(clientRequest);
        }
    }

    public void close(){
        System.out.println("Client has closed!");
    }

    private String input(){
        String input = null;
        try {
            input = scannerManager.readLine();
        } catch (IllegalStateException e) {
            System.out.println("Scanner exception: " + e.getMessage());
        }
        return input;
    }

    private ClientRequest createRequest(CommandArgumentList command){
        return new ClientRequest(ClientStatus.REQUEST, command);
    }

    private CommandArgumentList handleCommand(String input) throws SystemInClosedException, ScannerException{
        return commandHandler.handle(input);
    }
}
