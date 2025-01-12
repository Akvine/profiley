package ru.akvine.profiley.rest.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.SecurityControllerMeta;
import ru.akvine.profiley.rest.dto.common.AuthRequest;
import ru.akvine.profiley.rest.dto.common.RegistrationRequest;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;
import ru.akvine.profiley.rest.validator.CredentialsValidator;
import ru.akvine.profiley.rest.validator.SecurityValidator;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.User;

@RestController
@RequiredArgsConstructor
public class SecurityController implements SecurityControllerMeta {
    private final UserService userService;
    private final SecurityManager securityManager;
    private final CredentialsValidator credentialsValidator;
    private final SecurityValidator securityValidator;

    @Override
    public Response registration(@RequestBody @Valid RegistrationRequest request,
                                 HttpServletRequest httpServletRequest) {
        securityValidator.verifyRegistrationRequest(request);
        User clientModel = userService.create(request.getEmail(), request.getPassword());
        securityManager.authenticate(clientModel, httpServletRequest);
        return new SuccessfulResponse();
    }

    @Override
    public Response auth(@RequestBody @Valid AuthRequest request,
                         HttpServletRequest httpServletRequest) {
        securityValidator.verifyAuthRequest(request);
        User user = credentialsValidator.validateCredentials(
                request.getEmail(),
                request.getPassword());
        securityManager.authenticate(user, httpServletRequest);
        return new SuccessfulResponse();
    }

    @Override
    public Response logout(HttpServletRequest request) {
        securityManager.doLogout(request);
        return new SuccessfulResponse();
    }
}
