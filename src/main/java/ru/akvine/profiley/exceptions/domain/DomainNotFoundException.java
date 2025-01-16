package ru.akvine.profiley.exceptions.domain;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException(String message) {
        super(message);
    }
}
