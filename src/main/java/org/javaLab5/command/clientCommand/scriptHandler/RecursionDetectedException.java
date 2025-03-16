package org.javaLab5.command.clientCommand.scriptHandler;

public class RecursionDetectedException extends RuntimeException {
    public RecursionDetectedException(String message) {
        super(message);
    }
}
