package ru.akvine.profiley.rest.dto.rule;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateRuleRequest {
    @NotBlank
    private String uuid;

    private String alias;

    private String pattern;

    private String domainName;
}
