package org.AstrosLab.inputManager;

import lombok.Setter;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerManager {
    @Getter
    @Setter
    private static Scanner fileScanner;
    private static final Scanner consoleScaner = new Scanner(System.in);
    private static Scanner scanner = consoleScaner;

    public static void setFileScanner(Scanner fileScannerOut) throws FileNotFoundException {
        fileScanner = fileScannerOut;
    }

    public static void setConsoleScanner() {
        scanner = consoleScaner;
    }

    public static void setMainFileScanner(){
        scanner = fileScanner;
    }

    public static String readline() {
        if (scanner == null || !scanner.hasNextLine()){
            ScannerManager.setConsoleScanner();
        }
        return scanner.nextLine().trim();
    }

    public static boolean hasNextLine() {
        if (scanner == null || !scanner.hasNextLine()){
            ScannerManager.setConsoleScanner();
        }
        return scanner.hasNextLine();
    }


}
