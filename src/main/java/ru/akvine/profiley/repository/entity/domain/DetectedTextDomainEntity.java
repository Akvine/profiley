package ru.akvine.profiley.repository.entity.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "DETECTED_TEXT_DOMAIN_ENTITY")
public class DetectedTextDomainEntity extends DetectedDomainEntity {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detectedTextDomainEntitySeq")
    @SequenceGenerator(name = "detectedTextDomainEntitySeq", sequenceName = "SEQ_DETECTED_TEXT_DOMAIN_ENTITY", allocationSize = 1000)
    private Long id;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "LINE_NUMBER", nullable = false)
    private long lineNumber;

    @Column(name = "IS_CORRECT", nullable = false)
    private boolean correct;
}
