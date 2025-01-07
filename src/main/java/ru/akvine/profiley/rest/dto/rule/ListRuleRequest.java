package ru.akvine.profiley.rest.dto.rule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ListRuleRequest {
    private String domainName;
    private boolean includeSystemDomains;
}
