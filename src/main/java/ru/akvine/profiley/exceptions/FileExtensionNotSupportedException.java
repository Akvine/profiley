package ru.akvine.profiley.exceptions;

public class FileExtensionNotSupportedException extends RuntimeException {
    public FileExtensionNotSupportedException(String message) {
        super(message);
    }
}
