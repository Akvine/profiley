package ru.akvine.profiley.rest.dto.profile;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class UpdateUserSettingsRequest {
    private Boolean disabledSystemDomains;

    private Boolean disabledSystemRules;

    private String language;

    private Set<String> disabledSystemDomainsNames;
}
