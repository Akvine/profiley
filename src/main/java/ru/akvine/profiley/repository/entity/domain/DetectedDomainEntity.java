package ru.akvine.profiley.repository.entity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.akvine.profiley.repository.entity.ProcessEntity;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@MappedSuperclass
public abstract class DetectedDomainEntity {
    @Column(name = "UUID", nullable = false, updatable = false)
    @NotNull
    private String uuid = "STUB_UUID";

    @ManyToOne
    @JoinColumn(name = "PROCESS_ID", nullable = false)
    private ProcessEntity process;

    @Column(name = "DOMAIN_NAME", nullable = false)
    @NotNull
    private String domainName;
}
