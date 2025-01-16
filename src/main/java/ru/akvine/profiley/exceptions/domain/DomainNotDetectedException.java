package ru.akvine.profiley.exceptions.domain;

public class DomainNotDetectedException extends RuntimeException {
    public DomainNotDetectedException(String message) {
        super(message);
    }
}
