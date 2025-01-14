package ru.akvine.profiley.services.validators;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ValidatorType;

@Service
public class ImeiValidator extends CommonDomainValidator {
    @Override
    public boolean validate(String imei) {
        super.validate(imei);
        int sum = 0;
        for (int i = 0; i < 14; i++) {
            int digit = Character.getNumericValue(imei.charAt(i));
            if (i % 2 == 1) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        int controlDigit = (10 - (sum % 10)) % 10;
        return controlDigit == Character.getNumericValue(imei.charAt(14));
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.IMEI;
    }
}
