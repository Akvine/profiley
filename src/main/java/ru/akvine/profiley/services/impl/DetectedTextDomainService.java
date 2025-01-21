package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.repository.DetectedTextDomainRepository;
import ru.akvine.profiley.repository.entity.ProcessEntity;
import ru.akvine.profiley.repository.entity.domain.DetectedTextDomainEntity;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;
import ru.akvine.profiley.services.domain.domain.DetectedTextDomain;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetectedTextDomainService extends AbstractDetectedDomainService<DetectedTextDomain> {
    private final ProcessService processService;

    private final DetectedTextDomainRepository detectedTextDomainRepository;

    @Override
    public List<DetectedTextDomain> saveAll(String pid, String userUuid, List<? extends DetectedDomain> detectedTextDomains) {
        List<DetectedTextDomain> castedTextDomains = super.saveAll(pid, userUuid, detectedTextDomains);

        ProcessEntity processEntity = processService.verifyExists(pid, userUuid);
        List<DetectedTextDomainEntity> detectedTextDomainEntities = castedTextDomains.stream()
                .map(detectedDomain -> (DetectedTextDomainEntity) new DetectedTextDomainEntity()
                        .setCorrect(detectedDomain.isCorrect())
                        .setValue(detectedDomain.getValue())
                        .setLineNumber(detectedDomain.getLineNumber())
                        .setProcess(processEntity)
                        .setDomainName(detectedDomain.getDomainName())).toList();
        return detectedTextDomainRepository
                .saveAll(detectedTextDomainEntities)
                .stream()
                .map(DetectedTextDomain::new)
                .toList();
    }

    @Override
    public List<DetectedTextDomain> cast(List<? extends DetectedDomain> detectedDomains) {
        return detectedDomains.stream()
                .map(domain -> (DetectedTextDomain) domain)
                .toList();
    }

    @Override
    public FileType getType() {
        return FileType.TEXT;
    }

    @Override
    public Class<?> getClazz() {
        return DetectedTextDomain.class;
    }
}
