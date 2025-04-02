package org.javaLab5.collection;

public class IdMustBeUniqueException extends IllegalArgumentException {
    public IdMustBeUniqueException(String message) {
        super(message);
    }
}
