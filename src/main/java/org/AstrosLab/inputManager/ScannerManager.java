package org.AstrosLab.inputManager;

import lombok.Setter;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerManager {
    @Getter
    @Setter
    private static Scanner fileScanner;
    private static final Scanner consoleScanner = new Scanner(System.in);
    private static Scanner scanner = consoleScanner;
    private static Scanner tempScanner = consoleScanner;

    public static void setFileScanner(Scanner fileScannerOut) throws FileNotFoundException {
        fileScanner = fileScannerOut;
    }

    public static void setConsoleScanner() {
        scanner = consoleScanner;
    }

    public static void setMainFileScanner(){
        scanner = fileScanner;
    }

    public static void saveScanner(){
        tempScanner = scanner;
    }

    public static void loadScanner(){
        scanner = tempScanner;
    }

    public static void closeFileScanner(){
        if (fileScanner != null){
            fileScanner.close();
        }
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
