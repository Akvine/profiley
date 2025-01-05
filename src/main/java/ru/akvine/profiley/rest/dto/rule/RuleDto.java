package ru.akvine.profiley.rest.dto.rule;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RuleDto {
    @NotBlank
    private String uuid;
    private String alias;
    @NotBlank
    private String pattern;
}
