package ru.akvine.profiley.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.constants.ErrorCodes;
import ru.akvine.profiley.exceptions.common.ValidationException;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements Validator<String> {

    @Value("${security.min.password.length}")
    private int minPasswordLength;
    @Value("${security.max.password.length}")
    private int maxPasswordLength;


    private static final Pattern BIG_SYMBOLS_PATTERN = Pattern.compile("[A-Z]+");
    private static final Pattern SMALL_SYMBOLS_PATTERN = Pattern.compile("[a-z]+");
    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+");
    private static final Pattern SYMBOLS_PATTERN = Pattern.compile("[!\"#\\$%&\\(\\)``\\*\\+,-\\/:;<=>\\?_]+");

    @Override
    public void validate(String password) {
        if (StringUtils.isBlank(password)
                || password.length() < minPasswordLength
                || password.length() > maxPasswordLength) {
            throw new ValidationException(
                    ErrorCodes.Validation.PASSWORD_INVALID_ERROR,
                    "Password is invalid"
            );
        }

        if (password.contains(" ")) {
            throw new ValidationException(
                    ErrorCodes.Validation.PASSWORD_INVALID_ERROR,
                    "Password is invalid"
            );
        }

        long findCount = 0;
        if (BIG_SYMBOLS_PATTERN.matcher(password).find()) {
            ++findCount;
        }

        if (SMALL_SYMBOLS_PATTERN.matcher(password).find()) {
            ++findCount;
        }

        if (DIGITS_PATTERN.matcher(password).find()) {
            ++findCount;
        }

        if (SYMBOLS_PATTERN.matcher(password).find()) {
            ++findCount;
        }

        if (findCount < 3) {
            throw new ValidationException(
                    ErrorCodes.Validation.PASSWORD_INVALID_ERROR,
                    "Password is invalid!"
            );
        }
    }
}
