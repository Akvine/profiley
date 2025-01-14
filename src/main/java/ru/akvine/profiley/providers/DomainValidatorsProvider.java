package ru.akvine.profiley.providers;

import ru.akvine.profiley.enums.ValidatorType;
import ru.akvine.profiley.services.validators.DomainValidator;

import java.util.Map;

public record DomainValidatorsProvider(Map<ValidatorType, DomainValidator> domainValidators) {
    public DomainValidator getByType(ValidatorType type) {
        if (!domainValidators.containsKey(type)) {
            throw new IllegalArgumentException("Domain validator type = [" + type + "] is not supported by app!");
        }
        return domainValidators.get(type);
    }
}
