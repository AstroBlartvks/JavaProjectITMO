package org.javaLab5.command.clientCommand.scriptHandler;

import org.javaLab5.inputManager.NewScannerManager;

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
    private static final Stack<String> scriptStack = new Stack<>();
    private final NewScannerManager newScannerManager;

    public ScriptExecutes(NewScannerManager newScannerManager) {
        this.newScannerManager = newScannerManager;
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

        findRecursion(scriptName);

        try {
            Scanner scriptScanner = new Scanner(new File(scriptName));
            newScannerManager.setFileScanner(scriptScanner);
            newScannerManager.activeFile();
        } catch (FileNotFoundException exception) {
            throw new ScriptExecuteScannerException("Script-file '" + scriptName + "' doesn't exist!");
        } catch (NoSuchElementException exception) {
            throw new ScriptExecuteScannerException("Script-file '" + scriptName + "' is empty!");
        } catch (IllegalStateException exception) {
            throw new ScriptExecuteScannerException("Unexpected error!");
        } catch (Exception e){
            throw new ScriptExecuteScannerException("Unexpected exception " + e.getMessage());
        }

        addScriptToStack(scriptName);
    }

    private void findRecursion(String scriptName){
        if (!scriptStack.contains(scriptName)) {
            return;
        }
        String fileSeq = scriptStack.toString();
        scriptStack.clear();
        newScannerManager.closeFileScanner();
        newScannerManager.activeConsole();
        throw new RecursionDetectedException("Recursion in files detected: " + fileSeq + "!");
    }

    private void addScriptToStack(String name){
        scriptStack.push(name);
    }

    public static void popLastScript(){
        scriptStack.pop();
    }
}
