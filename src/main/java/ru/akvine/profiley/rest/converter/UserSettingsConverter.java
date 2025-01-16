package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.profile.UpdateUserSettingsRequest;
import ru.akvine.profiley.rest.dto.profile.UserSettingsResponse;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.services.dto.user.UpdateUser;
import ru.akvine.profiley.utils.Asserts;

@Component
@RequiredArgsConstructor
public class UserSettingsConverter {
    private final SecurityManager securityManager;

    public UserSettingsResponse convertToUserSettingsResponse(User user) {
        return new UserSettingsResponse()
                .setUuid(user.getUuid())
                .setEmail(user.getEmail())
                .setSystemDomainsDisabled(user.isDisabledSystemDomains())
                .setSystemRulesDisabled(user.isDisabledSystemRules());
    }

    public UpdateUser convertToUpdateUser(UpdateUserSettingsRequest request) {
        Asserts.isNotNull(request, "updateUserSettingsRequest is null");
        return new UpdateUser()
                .setUserUuid(securityManager.getCurrentUser().getUuid())
                .setDisabledSystemDomains(request.getDisabledSystemDomains())
                .setDisabledSystemRules(request.getDisabledSystemRules());
    }
}
