package ru.akvine.profiley.services.domain.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.akvine.profiley.repository.entity.domain.DetectedTextDomainEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DetectedTextDomain extends DetectedDomain {
    private Long id;
    private String value;
    private long lineNumber;
    private boolean correct;

    public DetectedTextDomain(DetectedTextDomainEntity detectedTextDomainEntity) {
        this.id = detectedTextDomainEntity.getId();
        this.process = detectedTextDomainEntity.getProcess();
        this.domainName = detectedTextDomainEntity.getDomainName();

        this.value = detectedTextDomainEntity.getValue();
        this.lineNumber = detectedTextDomainEntity.getLineNumber();
        this.correct = detectedTextDomainEntity.isCorrect();

    }
}
