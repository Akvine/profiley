package ru.akvine.profiley.services.dto.user;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateUser {
    private String userUuid;

    @Nullable
    private Boolean disabledSystemDomains;
    @Nullable
    private Boolean disabledSystemRules;
}
