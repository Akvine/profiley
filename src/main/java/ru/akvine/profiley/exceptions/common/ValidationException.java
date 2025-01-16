package ru.akvine.profiley.exceptions.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private final String errorCode;
    private final String message;
}
