package ru.akvine.profiley.services;

import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;

import java.util.List;

public interface DetectedDomainService<T extends DetectedDomain> {
    List<T> saveAll(String pid, String userUuid, List<? extends DetectedDomain> detectedDomains);

    FileType getType();

    Class<?> getClazz();
}
