package org.AstrosLab.inputManager;

import java.util.Scanner;

public class InputManager {
    private final Scanner scanner = ScannerManager.getScanner();;


    public boolean hasNextInput() {
        return this.scanner.hasNextLine();
    }

    public String[] input(){
        String[] userCommand = scanner.nextLine().trim().split(" ");
        return userCommand;
    }

}
