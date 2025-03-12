package org.AstrosLab.inputManager;

import java.util.Scanner;

public class InputManager {
    private final Scanner scanner = ScannerManager.getScanner();;


    public boolean hasNextInput() {
        System.out.print(">>> ");
        return this.scanner.hasNext();
    }

    public String input(){
        return scanner.nextLine().trim();
    }

}
