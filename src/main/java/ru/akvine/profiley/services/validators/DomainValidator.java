package ru.akvine.profiley.services.validators;

import ru.akvine.profiley.enums.ValidatorType;

public interface DomainValidator {
    boolean validate(String value);

    ValidatorType getType();
}
