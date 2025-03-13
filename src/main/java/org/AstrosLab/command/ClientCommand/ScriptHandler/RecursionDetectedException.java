package org.AstrosLab.command.ClientCommand.ScriptHandler;

public class RecursionDetectedException extends RuntimeException {
    public RecursionDetectedException(String message) {
        super(message);
    }
}
