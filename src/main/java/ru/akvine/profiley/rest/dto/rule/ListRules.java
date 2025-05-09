package ru.akvine.profiley.rest.dto.rule;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ListRules {
    private String userUuid;
    @Nullable
    private String domainName;
    private boolean includeSystemDomains;
}
