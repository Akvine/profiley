package ru.akvine.profiley.services.domain.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.repository.entity.ProcessEntity;

@Data
@Accessors(chain = true)
public abstract class DetectedDomain {
    protected ProcessEntity process;

    protected String domainName;
}
