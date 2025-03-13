package org.AstrosLab.inputManager;


public class InputManager {
    public boolean hasNextInput() {
        System.out.print(">>> ");
        return ScannerManager.hasNextLine();
    }

    public String input(){
        return ScannerManager.readline();
    }

}
