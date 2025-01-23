package ru.akvine.profiley.services.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class ListDomains {
    private String userUuid;
    private Collection<String> domainNames;
    private boolean includeSystem;
}
