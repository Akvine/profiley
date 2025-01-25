package ru.akvine.profiley.rest.converter;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.enums.Language;
import ru.akvine.profiley.rest.dto.profile.UpdateUserSettingsRequest;
import ru.akvine.profiley.rest.dto.profile.UserSettingsResponse;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.services.dto.user.UpdateUser;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.StringHelper;

@Component
@RequiredArgsConstructor
public class UserSettingsConverter {
    private final SecurityManager securityManager;

    public UserSettingsResponse convertToUserSettingsResponse(User user) {
        String disabledSystemDomainsNames = null;
        if (CollectionUtils.isNotEmpty((user.getDisabledSystemDomainsNames()))) {
            disabledSystemDomainsNames = StringHelper.replaceAroundMiddle(
                    user.getDisabledSystemDomainsNames().stream().toList(), 2);
        }

        return new UserSettingsResponse()
                .setUuid(user.getUuid())
                .setEmail(user.getEmail())
                .setSystemDomainsDisabled(user.isDisabledSystemDomains())
                .setSystemRulesDisabled(user.isDisabledSystemRules())
                .setLanguage(user.getLanguage().toString())
                .setDisabledSystemDomainsNames(disabledSystemDomainsNames);
    }

    public UpdateUser convertToUpdateUser(UpdateUserSettingsRequest request) {
        Asserts.isNotNull(request, "updateUserSettingsRequest is null");

        String disabledSystemDomainsNames;
        if (request.getDisabledSystemDomainsNames() == null) {
            disabledSystemDomainsNames = null;
        } else if (request.getDisabledSystemDomainsNames().isEmpty()) {
            disabledSystemDomainsNames = "";
        } else {
            disabledSystemDomainsNames = String.join(",", request.getDisabledSystemDomainsNames());
        }

        return new UpdateUser()
                .setUserUuid(securityManager.getCurrentUser().getUuid())
                .setDisabledSystemDomains(request.getDisabledSystemDomains())
                .setDisabledSystemRules(request.getDisabledSystemRules())
                .setDisabledSystemDomainsNames(disabledSystemDomainsNames)
                .setLanguage(StringUtils.isBlank(request.getLanguage()) ? null : Language.from(request.getLanguage()));
    }
}
