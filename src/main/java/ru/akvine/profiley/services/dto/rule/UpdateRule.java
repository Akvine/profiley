package ru.akvine.profiley.services.dto.rule;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateRule {
    private String uuid;

    private String userUuid;

    @Nullable
    private String alias;

    @Nullable
    private String pattern;

    @Nullable
    private String domainName;
}
