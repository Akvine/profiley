package ru.akvine.profiley.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.profiley.rest.dto.common.AuthRequest;
import ru.akvine.profiley.rest.dto.common.RegistrationRequest;
import ru.akvine.profiley.rest.dto.common.Response;

@RequestMapping(value = "/security")
public interface SecurityControllerMeta {
    @PostMapping(value = "/registration")
    Response registration(@RequestBody @Valid RegistrationRequest request, HttpServletRequest httpServletRequest);

    @PostMapping(value = "/auth")
    Response auth(@RequestBody @Valid AuthRequest request, HttpServletRequest httpServletRequest);

    @PostMapping(value = "/logout")
    Response logout(HttpServletRequest request);
}
