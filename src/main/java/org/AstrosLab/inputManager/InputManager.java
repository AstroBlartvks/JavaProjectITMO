package org.AstrosLab.inputManager;

import java.util.Scanner;

public class InputManager {
    private final Scanner scanner = ScannerManager.getScanner();;


    public boolean hasNextInput() {
        System.out.print(">>> ");
        return this.scanner.hasNext();
    }

    public String input(){
        String userCommand = scanner.nextLine().trim().split(" ")[0];
        return userCommand;
    }

}
