package ru.akvine.profiley.rest.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DomainDto {
    private String name;
    private boolean system;
}
