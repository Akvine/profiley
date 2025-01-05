package ru.akvine.profiley.rest.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ListDomainRequest {
    private boolean includeSystems = true;
}
