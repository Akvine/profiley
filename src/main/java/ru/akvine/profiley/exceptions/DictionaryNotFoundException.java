package ru.akvine.profiley.exceptions;

public class DictionaryNotFoundException extends RuntimeException {
    public DictionaryNotFoundException(String message) {
        super(message);
    }
}
