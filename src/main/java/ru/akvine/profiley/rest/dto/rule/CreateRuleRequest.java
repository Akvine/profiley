package ru.akvine.profiley.rest.dto.rule;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateRuleRequest {
    @NotBlank
    private String domainName;

    private String alias;

    @NotBlank
    private String pattern;

}
