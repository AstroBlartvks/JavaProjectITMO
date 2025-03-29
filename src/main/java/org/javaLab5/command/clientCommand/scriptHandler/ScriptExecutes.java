package org.javaLab5.command.clientCommand.scriptHandler;

import org.javaLab5.inputManager.ScannerManager;
import org.javaLab5.inputManager.SmartScanner;
import org.javaLab5.inputManager.SmartScannerType;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Handles the execution of script files, ensuring proper command execution
 * while preventing recursion loops.
 */
public class ScriptExecutes {
    private final ScannerManager scannerManager;

    public ScriptExecutes(ScannerManager scannerManager) {
        this.scannerManager = scannerManager;
    }

    /**
     * Running a script file by reading and processing each line as a command.
     *
     * @param scriptName The name of the script file to execute.
     * @throws Exception If the script file does not exist, is unreadable, or recursion is detected.
     */
    public void run(String scriptName) throws Exception {

        if (!new File(scriptName).exists()) {
            throw new Exception("Script-file '" + scriptName + "' doesn't exist!");
        }
        if (!Files.isReadable(Paths.get(scriptName))) {
            throw new Exception("Insufficient permissions to read the '" + scriptName + "' script-file");
        }

        if (isItRecursion(scriptName)){
            return;
        }

        try {
            Scanner scriptScanner = new Scanner(new File(scriptName));
            SmartScanner smartScanner = new SmartScanner(scriptScanner, SmartScannerType.FILE, scriptName);
            scannerManager.pushScanner(smartScanner);
            scannerManager.setLastScannerAsActive();

        } catch (FileNotFoundException exception) {
            throw new ScriptExecuteScannerException("Script-file '" + scriptName + "' doesn't exist!");
        } catch (NoSuchElementException exception) {
            throw new ScriptExecuteScannerException("Script-file '" + scriptName + "' is empty!");
        } catch (IllegalStateException exception) {
            throw new ScriptExecuteScannerException("Unexpected error!");
        } catch (Exception e){
            throw new ScriptExecuteScannerException("Unexpected exception " + e.getMessage());
        }

    }

    private boolean isItRecursion(String scriptName){
        boolean alreadyExists = scannerManager.getScannersStack().stream()
                .anyMatch(scriptFile -> scriptFile.getName().equals(scriptName));

        if (!alreadyExists) {
            return false;
        }

        System.out.println("Recursion in file detected: " + scriptName + "!");
        return true;
    }

}
