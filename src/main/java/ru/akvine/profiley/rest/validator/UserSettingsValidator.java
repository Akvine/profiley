package ru.akvine.profiley.rest.validator;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.rest.dto.profile.UpdateUserSettingsRequest;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.validators.LanguageValidator;

@Component
@RequiredArgsConstructor
public class UserSettingsValidator {
    private final LanguageValidator languageValidator;

    public void verifyUpdateUserSettingsRequest(UpdateUserSettingsRequest request) {
        Asserts.isNotNull(request, "updateUserSettingsRequest is null");
        if (StringUtils.isNotBlank(request.getLanguage())) {
            languageValidator.validate(request.getLanguage());
        }
    }
}
