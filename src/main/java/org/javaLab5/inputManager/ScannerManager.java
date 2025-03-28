package org.javaLab5.inputManager;

import lombok.Getter;
import lombok.Setter;
import org.javaLab5.command.clientCommand.scriptHandler.ScriptExecutes;

import java.util.Scanner;
import java.util.Stack;


/**
 * The {@code ScannerManager} class manages the {@link Scanner} instances used for reading input from various sources.
 * It provides functionality to switch between reading from the console and reading from a file. The class also allows
 * saving and restoring the current {@code Scanner} state.
 * <p>
 * The class utilizes {@link lombok.Getter} and {@link lombok.Setter} annotations for the {@code fileScanner} property
 * to automatically generate getter and setter methods.
 */
@Setter
@Getter
public class ScannerManager {
    /**
     * The current {@code Scanner} being used for input. It may point to either the console or a file.
     */
    private Scanner activeScanner;
    /**
     * The {@code Scanner} instance used for reading input from a file.
     */
    private Scanner fileScanner;
    /**
     * The current {@code Scanner} being used for input. It may point to either the console or a file.
     */
    private Scanner consoleScanner;
    /**
     * A temporary {@code Scanner} instance used for saving the current scanner state.
     */
    private Scanner memScanner;

    private static final Stack<Scanner> fileScannerStack = new Stack<>();

    public ScannerManager(){
        this.consoleScanner = new Scanner(System.in);
        this.fileScanner = null;
        this.activeConsole();
    }

    public void pushScannerToStack(Scanner scanner){
        fileScannerStack.push(scanner);
    }

    public Scanner popScannerFromStack(){
        return fileScannerStack.pop();
    }

    public Scanner getLastScannerFromStack(){
        return fileScannerStack.peek();
    }

    public boolean isScannerStackEmpty(){
        return fileScannerStack.isEmpty();
    }

    public void clearScannerStack(){
        fileScannerStack.clear();
    }

    /**
     * Reads a line of input from the current {@code Scanner}.
     * <p>
     * If the current {@code Scanner} is {@code null} or has no next line, it switches to the console scanner.
     *
     * @return The line of input read from the current {@code Scanner}.
     */
    public String readLine(){
        if (this.activeScanner == null){
            this.activeConsole();
        }

        String line = this.activeScanner.nextLine();
        if (line.isEmpty()){
            return "";
        }

        if (this.activeScanner == this.fileScanner){
            System.out.println(line.trim());
        }
        return line.trim();
    }

    /**
     * Checks if there is a next line of input available from the current {@code Scanner}.
     * <p>
     * If the current {@code Scanner} is {@code null} or has no next line, it switches to the console scanner.
     *
     * @return {@code true} if there is another line of input available, {@code false} otherwise.
     */
    public boolean hasNextLine(){
        System.out.print(">>> ");
        boolean isNextLine = this.activeScanner.hasNextLine();

        if (!isNextLine && (activeScanner == fileScanner)){
            popScannerFromStack().close();
            if (!isScannerStackEmpty()) {
                activeScanner = getLastScannerFromStack();
                fileScanner = activeScanner;
            } else {
                activeConsole();
            }
            return true;
        }

        return isNextLine;
    }

    public void activeConsole(){
        this.activeScanner = this.consoleScanner;
    }

    public void activeFile(){
        this.activeScanner = this.fileScanner;
    }

    public void closeFileScanner(){
        this.fileScanner.close();
    }

    /**
     * Saves the current {@code Scanner} instance to the temporary scanner.
     * This method allows restoring the previous {@code Scanner} state later.
     */
    public void saveActiveScanner(){
        memScanner = activeScanner;
    }

    /**
     * Restores the {@code Scanner} to the previous state saved by {@link #saveActiveScanner()}.
     */
    public void loadActiveScanner(){
        activeScanner = memScanner;
    }
}
