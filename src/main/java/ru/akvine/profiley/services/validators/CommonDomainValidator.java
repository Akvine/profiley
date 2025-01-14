package ru.akvine.profiley.services.validators;

import ru.akvine.profiley.utils.Asserts;

public abstract class CommonDomainValidator implements DomainValidator {
    @Override
    public boolean validate(String value) {
        Asserts.isNotNull(value, "Value in " + CommonDomainValidator.class.getSimpleName() + " is null!");
        return false;
    }
}
