package org.javaLab5.command.clientCommand;

public class UndefinedCommandException extends RuntimeException {
    public UndefinedCommandException(String message) {
        super(message);
    }
}
