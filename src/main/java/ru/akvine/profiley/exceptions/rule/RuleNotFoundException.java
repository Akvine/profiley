package ru.akvine.profiley.exceptions.rule;

public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException(String message) {
        super(message);
    }
}
