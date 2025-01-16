package ru.akvine.profiley.exceptions.file;

public class FileExtensionNotSupportedException extends RuntimeException {
    public FileExtensionNotSupportedException(String message) {
        super(message);
    }
}
