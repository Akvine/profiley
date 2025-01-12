package ru.akvine.profiley.validators;

public interface Validator<T> {
    void validate(T object);
}
