package org.javaLab5.inputManager;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

/**
 * The {@code ScannerManager} class manages the {@link Scanner} instances used for reading input from various sources.
 * It provides functionality to switch between reading from the console and reading from a file. The class also allows
 * saving and restoring the current {@code Scanner} state.
 * <p>
 * The class utilizes {@link lombok.Getter} and {@link lombok.Setter} annotations for the {@code fileScanner} property
 * to automatically generate getter and setter methods.
 */
public class ScannerManager {

    /**
     * The {@code Scanner} instance used for reading input from a file.
     */
    @Setter
    @Getter
    private static Scanner fileScanner;

    /**
     * The {@code Scanner} instance used for reading input from the console.
     */
    private static final Scanner consoleScanner = new Scanner(System.in);

    /**
     * The current {@code Scanner} being used for input. It may point to either the console or a file.
     */
    private static Scanner scanner = consoleScanner;

    /**
     * A temporary {@code Scanner} instance used for saving the current scanner state.
     */
    private static Scanner tempScanner = consoleScanner;

    /**
     * Sets the {@code Scanner} to the console scanner, switching input source to the console.
     */
    public static void setConsoleScanner() {
        scanner = consoleScanner;
    }

    /**
     * Sets the {@code Scanner} to the file scanner, switching input source to a file.
     */
    public static void setMainFileScanner(){
        scanner = fileScanner;
    }

    /**
     * Saves the current {@code Scanner} instance to the temporary scanner.
     * This method allows restoring the previous {@code Scanner} state later.
     */
    public static void saveScanner(){
        tempScanner = scanner;
    }

    /**
     * Restores the {@code Scanner} to the previous state saved by {@link #saveScanner()}.
     */
    public static void loadScanner(){
        scanner = tempScanner;
    }

    /**
     * Closes the file {@code Scanner} if it is not {@code null}.
     * This method should be called when done reading from a file to release the resource.
     */
    public static void closeFileScanner(){
        if (fileScanner != null){
            fileScanner.close();
        }
    }

    /**
     * Reads a line of input from the current {@code Scanner}.
     * <p>
     * If the current {@code Scanner} is {@code null} or has no next line, it switches to the console scanner.
     *
     * @return The line of input read from the current {@code Scanner}.
     */
    public static String readLine() {
        if (scanner == null || !scanner.hasNextLine()){
            ScannerManager.setConsoleScanner();
        }
        return scanner.nextLine().trim();
    }

    /**
     * Checks if there is a next line of input available from the current {@code Scanner}.
     * <p>
     * If the current {@code Scanner} is {@code null} or has no next line, it switches to the console scanner.
     *
     * @return {@code true} if there is another line of input available, {@code false} otherwise.
     */
    public static boolean hasNextLine() {
        if (scanner == null || !scanner.hasNextLine()){
            ScannerManager.setConsoleScanner();
        }
        return scanner.hasNextLine();
    }
}
