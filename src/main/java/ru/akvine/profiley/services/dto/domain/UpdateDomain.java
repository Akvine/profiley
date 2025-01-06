package ru.akvine.profiley.services.dto.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateDomain {
    private String userUuid;
    private String domainName;
    @Nullable
    private String newDomainName;
}
