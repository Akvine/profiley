package ru.akvine.profiley.services.validators;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ValidatorType;

@Service
public class SnilsValidator extends CommonDomainValidator {
    @Override
    public boolean validate(String snils) {
        super.validate(snils);
        String snilsNumber = snils.replaceAll("[^\\d]", "").substring(0, 9);
        int checksum = Integer.parseInt(snils.replaceAll("[^\\d]", "").substring(9, 11));
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(snilsNumber.charAt(i)) * (9 - i);
        }
        int calculatedChecksum = sum < 100 ? sum : sum % 101;
        if (calculatedChecksum == 100) {
            calculatedChecksum = 0;
        }
        return checksum == calculatedChecksum;
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.SNILS;
    }
}
