package ru.akvine.profiley.exceptions.domain;

public class DomainAlreadyExistsException extends RuntimeException {
    public DomainAlreadyExistsException(String message) {
        super(message);
    }
}
