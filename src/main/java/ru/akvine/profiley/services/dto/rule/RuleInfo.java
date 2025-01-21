package ru.akvine.profiley.services.dto.rule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RuleInfo {
    private String domainName;
    private String value;
    private boolean correct;
}
