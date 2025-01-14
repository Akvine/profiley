package ru.akvine.profiley.services.validators;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ValidatorType;

@Service
public class OgrnipValidator extends CommonDomainValidator {
    @Override
    public boolean validate(String ogrnip) {
        super.validate(ogrnip);
        String base = ogrnip.substring(0, 14);
        long controlDigit = Long.parseLong(base) % 13 % 10;
        return controlDigit == Character.getNumericValue(ogrnip.charAt(14));
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.OGRNIP;
    }
}
