package org.AstroLab.files;

public class FileIsNotExistException extends RuntimeException {
    public FileIsNotExistException(String message) {
        super(message);
    }
}
