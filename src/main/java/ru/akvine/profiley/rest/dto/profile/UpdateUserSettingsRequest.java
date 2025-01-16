package ru.akvine.profiley.rest.dto.profile;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateUserSettingsRequest {
    private Boolean disabledSystemDomains;

    private Boolean disabledSystemRules;
}
