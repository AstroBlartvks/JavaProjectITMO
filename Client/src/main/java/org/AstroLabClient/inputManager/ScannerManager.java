package org.AstroLabClient.inputManager;

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
        SmartScanner consoleScanner = getNewSmartScanner();
        pushScanner(consoleScanner);
        activeSmartScanner = consoleScanner;
    }

    public void pushScanner(SmartScanner scanner){
        scannersStack.push(scanner);
    }

    public void setLastScannerAsActive(){
        activeSmartScanner = scannersStack.peek();
    }

    public void checkIfSystemInClosed() throws SystemInClosedException{
        if (!activeSmartScanner.hasNextLine() && activeSmartScanner.getType() == SmartScannerType.CONSOLE){
            throw new SystemInClosedException("System.in closed by your CTRL+D, program will be closed!");
        }
    }

    public void setConsoleScanner() throws ScannerException{
        if (scannersStack.isEmpty()){
            throw new ScannerException("Console scanner was closed!");
        }

        activeSmartScanner = scannersStack.firstElement();
    }

    private SmartScanner getNewSmartScanner(){
        return new SmartScanner(new Scanner(System.in), SmartScannerType.CONSOLE, "CONSOLE");
    }

    /**
     * Reads a line of input from the current {@code Scanner}.
     * <p>
     * If the current {@code Scanner} is {@code null} or has no next line, it switches to the console scanner.
     *
     * @return The line of input read from the current {@code Scanner}.
     */
    public String readLine() throws ScannerException{
        if (activeSmartScanner.isClosed()){
            throw new ScannerException("Scanner closed!");
        }

        if (!activeSmartScanner.hasNextLine()){
            scannersStack.pop().close();
            if (!scannersStack.isEmpty()) {
                setLastScannerAsActive();
            } else {
                throw new ScannerException("Scanner has ended!");
            }
        }

        return activeSmartScanner.nextLine();
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

        if (activeSmartScanner.isClosed() && scannersStack.isEmpty()){
            return false;
        }

        boolean isNextLine = activeSmartScanner.hasNextLine();

        if (!isNextLine){

            if (activeSmartScanner.getType() == SmartScannerType.CONSOLE){
                return false;
            }

            if (scannersStack.isEmpty()){
                scannersStack.push(getNewSmartScanner());
                activeSmartScanner = scannersStack.firstElement();
                return true;
            }

            scannersStack.pop().close();
            if (scannersStack.isEmpty()){
                return false;
            }
            activeSmartScanner = scannersStack.peek();
        }

        return !scannersStack.isEmpty();
    }

    public void close(){
        for (SmartScanner stack : scannersStack){
            stack.close();
        }
    }

}
