package org.AstrosLab.inputManager;

import java.util.Scanner;

public class InputManager {
    public boolean hasNextInput() {
        System.out.print(">>> ");
        return ScannerManager.hasNextLine();
    }

    public String input(){
        return ScannerManager.readline();
    }

}
