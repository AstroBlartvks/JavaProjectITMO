package org.javaLab5.inputManager;

import lombok.Getter;
import lombok.Setter;

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
    private final Stack<SmartScanner> scannersStack = new Stack<>();
    private SmartScanner activeSmartScanner;

    public ScannerManager(){
        SmartScanner consoleScanner = getSmartScanner();
        pushScanner(consoleScanner);
        activeSmartScanner = consoleScanner;
    }

    public void pushScanner(SmartScanner scanner){
        scannersStack.push(scanner);
    }

    public void setLastScannerAsActive(){
        activeSmartScanner = scannersStack.peek();
    }

    public void setConsoleScanner(){
        activeSmartScanner = scannersStack.firstElement();
    }

    private SmartScanner getSmartScanner(){
        return new SmartScanner(new Scanner(System.in), SmartScannerType.CONSOLE, "CONSOLE");
    }

    /**
     * Reads a line of input from the current {@code Scanner}.
     * <p>
     * If the current {@code Scanner} is {@code null} or has no next line, it switches to the console scanner.
     *
     * @return The line of input read from the current {@code Scanner}.
     */
    public String readLine() throws IllegalStateException{
        if (activeSmartScanner.isClosed() || !activeSmartScanner.hasNextLine()){
            throw new IllegalStateException("Scanner closed!");
        }
        return activeSmartScanner.nextLine();
    }

    public void reCreateScannerManager(){
        scannersStack.clear();
        SmartScanner consoleScanner = getSmartScanner();
        pushScanner(consoleScanner);
        activeSmartScanner = consoleScanner;
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

        boolean isNextLine = activeSmartScanner.hasNextLine();

        if (!isNextLine || activeSmartScanner.isClosed()){
            scannersStack.pop().close();
            if (scannersStack.isEmpty()){
                return false;
            }
            activeSmartScanner = scannersStack.peek();
        }

        return !scannersStack.isEmpty();
    }

}
