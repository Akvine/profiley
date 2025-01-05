package ru.akvine.profiley.rest.dto.rule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ListRules {
    private String userUuid;
    private String domainName;
}
