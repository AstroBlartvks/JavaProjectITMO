package org.AstrosLab.validate;

public class StringMustBeNotEmpty extends RuntimeException {
    public StringMustBeNotEmpty(String message) {
        super(message);
    }
}
