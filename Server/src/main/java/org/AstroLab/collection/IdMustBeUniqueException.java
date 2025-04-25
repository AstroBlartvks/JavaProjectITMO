package org.AstroLab.collection;

public class IdMustBeUniqueException extends IllegalArgumentException {
    public IdMustBeUniqueException(String message) {
        super(message);
    }
}
