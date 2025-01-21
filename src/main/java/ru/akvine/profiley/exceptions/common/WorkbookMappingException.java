package ru.akvine.profiley.exceptions.common;

public class WorkbookMappingException extends RuntimeException {
    public WorkbookMappingException(Exception exception) {
        super(exception);
    }
}
