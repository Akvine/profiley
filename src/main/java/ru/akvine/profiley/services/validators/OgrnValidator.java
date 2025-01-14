package ru.akvine.profiley.services.validators;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ValidatorType;

@Service
public class OgrnValidator extends CommonDomainValidator  {
    @Override
    public boolean validate(String ogrn) {
        super.validate(ogrn);
        String base = ogrn.substring(0, 12);
        long controlDigit = Long.parseLong(base) % 11 % 10;
        return controlDigit == Character.getNumericValue(ogrn.charAt(12));
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.OGRN;
    }
}
