package org.javaLab6.files;

public class FileIsNotExistException extends RuntimeException {
    public FileIsNotExistException(String message) {
        super(message);
    }
}
