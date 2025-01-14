package ru.akvine.profiley.services.validators;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ValidatorType;

@Service
public class VinValidator extends CommonDomainValidator {
    @Override
    public boolean validate(String vin) {
        super.validate(vin);

        int[] weights = {8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};
        String transliteration = "0123456789.ABCDEFGH..JKLMN.P.R..STUVWXYZ";
        int sum = 0;

        for (int i = 0; i < vin.length(); i++) {
            char c = vin.charAt(i);
            int value = transliteration.indexOf(c) % 10;
            sum += value * weights[i];
        }

        int remainder = sum % 11;
        char expectedCheckDigit = (remainder == 10) ? 'X' : (char) ('0' + remainder);

        return vin.charAt(8) == expectedCheckDigit;
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.VIN;
    }
}
