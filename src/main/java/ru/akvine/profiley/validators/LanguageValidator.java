package ru.akvine.profiley.validators;

import org.springframework.stereotype.Component;
import ru.akvine.profiley.constants.ErrorCodes;
import ru.akvine.profiley.enums.Language;
import ru.akvine.profiley.exceptions.common.ValidationException;

@Component
public class LanguageValidator implements Validator<String> {
    @Override
    public void validate(String language) {
        try {
            Language.from(language);
        } catch (UnsupportedOperationException exception) {
            throw new ValidationException(
                    ErrorCodes.Validation.LANGUAGE_NOT_SUPPORTED_ERROR,
                    exception.getMessage()
            );
        }
    }
}
