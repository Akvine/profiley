package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.profile.UpdateUserSettingsRequest;

@RequestMapping(value = "/settings")
public interface UserSettingsControllerMeta {
    @GetMapping
    Response list();

    @PatchMapping
    Response update(@RequestBody @Valid UpdateUserSettingsRequest request);
}
