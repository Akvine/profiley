package ru.akvine.profiley.services.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateDomain {
    private String userUuid;
    private String name;
}
