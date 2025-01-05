package ru.akvine.profiley.services.dto.rule;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateRule {
    private String domainName;
    @Nullable
    private String alias;
    private String pattern;
}
