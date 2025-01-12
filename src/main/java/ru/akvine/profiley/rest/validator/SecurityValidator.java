package ru.akvine.profiley.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.rest.dto.common.AuthRequest;
import ru.akvine.profiley.rest.dto.common.RegistrationRequest;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.validators.EmailValidator;
import ru.akvine.profiley.validators.PasswordValidator;

@Component
@RequiredArgsConstructor
public class SecurityValidator {
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    public void verifyRegistrationRequest(RegistrationRequest request) {
        emailValidator.validate(request.getEmail());
        passwordValidator.validate(request.getPassword());
    }

    public void verifyAuthRequest(AuthRequest request) {
        Asserts.isNotNull(request, "authRequest is null");

        emailValidator.validate(request.getEmail());
    }
}
