package ru.akvine.profiley.rest.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

@Data
@Accessors(chain = true)
public class UserSettingsResponse extends SuccessfulResponse {
    @NotBlank
    private String uuid;

    @NotBlank
    private String email;

    private boolean systemDomainsDisabled;

    private boolean systemRulesDisabled;

    private String language;

    private String disabledSystemDomainsNames;
}
