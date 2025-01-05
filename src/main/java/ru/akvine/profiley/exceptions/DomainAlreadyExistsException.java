package ru.akvine.profiley.exceptions;

public class DomainAlreadyExistsException extends RuntimeException {
    public DomainAlreadyExistsException(String message) {
        super(message);
    }
}
