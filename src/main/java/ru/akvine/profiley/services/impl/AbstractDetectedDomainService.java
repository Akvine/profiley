package ru.akvine.profiley.services.impl;

import org.apache.commons.collections4.CollectionUtils;
import ru.akvine.profiley.exceptions.common.InvalidClassTypeException;
import ru.akvine.profiley.services.DetectedDomainService;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;
import ru.akvine.profiley.utils.Asserts;

import java.util.List;

public abstract class AbstractDetectedDomainService<T extends DetectedDomain> implements DetectedDomainService<T> {
    @Override
    public List<T> saveAll(String pid, String userUuid, List<? extends DetectedDomain> detectedDomains) {
        Asserts.isNotNull(pid, "pid is null");
        Asserts.isNotNull(userUuid, "userUuid is null");
        Asserts.isNotNull(detectedDomains, "detectedDomains is null");

        if (CollectionUtils.isNotEmpty(detectedDomains)) {
            Class<?> clazz = detectedDomains.getFirst().getClass();

            if (!clazz.equals(getClazz())) {
                String errorMessage = String.format(
                        "Class type in list = [%s] not equals target class = [%s]",
                        clazz.getSimpleName(), getType()
                );
                throw new InvalidClassTypeException(errorMessage);
            }
        }

        return cast(detectedDomains);
    }

    public abstract List<T> cast(List<? extends DetectedDomain> detectedDomains);
}
