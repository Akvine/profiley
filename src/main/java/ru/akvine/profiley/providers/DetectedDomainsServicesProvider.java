package ru.akvine.profiley.providers;

import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.services.DetectedDomainService;

import java.util.Map;

public record DetectedDomainsServicesProvider(Map<FileType, DetectedDomainService<?>> detectedDomainServiceMap) {
    public DetectedDomainService<?> getByType(FileType type) {
        if (!detectedDomainServiceMap.containsKey(type)) {
            throw new IllegalArgumentException("File type = [" + type + "] is not supported by app!");
        }
        return detectedDomainServiceMap.get(type);
    }
}
