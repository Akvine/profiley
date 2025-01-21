package ru.akvine.profiley.services;

import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;

import java.util.List;

public interface DetectedDomainService<T extends DetectedDomain> {
    List<T> saveAll(String pid, String userUuid, List<? extends DetectedDomain> detectedDomains);

    @Transactional
    List<T> list(String pid, String userUuid);

    FileType getType();

    Class<?> getClazz();
}
