package org.AstrosLab.command;

public class UnexpectedCommandException extends RuntimeException {
    public UnexpectedCommandException(String message) {
        super(message);
    }
}
