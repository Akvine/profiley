package ru.akvine.profiley.services.validators;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ValidatorType;

@Service
public class InnValidator extends CommonDomainValidator {
    @Override
    public boolean validate(String inn) {
        super.validate(inn);

        int[] coeff10 = {2, 4, 10, 3, 5, 9, 4, 6, 8};
        int[] coeff12_1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        int[] coeff12_2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        int length = inn.length();

        if (length == 10) {
            return checkControlSum(inn, coeff10) == Character.getNumericValue(inn.charAt(9));
        } else if (length == 12) {
            boolean firstCheck = checkControlSum(inn, coeff12_1) == Character.getNumericValue(inn.charAt(10));
            boolean secondCheck = checkControlSum(inn, coeff12_2) == Character.getNumericValue(inn.charAt(11));
            return firstCheck && secondCheck;
        }
        return false;
    }

    private int checkControlSum(String number, int[] coeffs) {
        int sum = 0;
        for (int i = 0; i < coeffs.length; i++) {
            sum += Character.getNumericValue(number.charAt(i)) * coeffs[i];
        }
        return sum % 11 % 10;
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.INN;
    }
}
