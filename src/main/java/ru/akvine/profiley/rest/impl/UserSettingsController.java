package ru.akvine.profiley.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.UserSettingsControllerMeta;
import ru.akvine.profiley.rest.converter.UserSettingsConverter;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.profile.UpdateUserSettingsRequest;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.services.dto.user.UpdateUser;

@RestController
@RequiredArgsConstructor
public class UserSettingsController implements UserSettingsControllerMeta {
    private final SecurityManager securityManager;
    private final UserService userService;
    private final UserSettingsConverter userSettingsConverter;

    @Override
    public Response list() {
        String currentUserUuid = securityManager.getCurrentUser().getUuid();
        User currentUser = userService.get(currentUserUuid);
        return userSettingsConverter.convertToUserSettingsResponse(currentUser);
    }

    @Override
    public Response update(@RequestBody UpdateUserSettingsRequest request) {
        UpdateUser updateUser = userSettingsConverter.convertToUpdateUser(request);
        User updatedUser = userService.update(updateUser);
        return userSettingsConverter.convertToUserSettingsResponse(updatedUser);
    }
}
