package ru.akvine.profiley.repository.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.ValidatorType;
import ru.akvine.profiley.repository.entity.domain.DomainEntity;

@Accessors(chain = true)
@Entity
@Table(name = "RULE_ENTITY")
@NoArgsConstructor
@Setter
@Getter
public class  RuleEntity extends BaseEntity<Long> {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruleEntitySeq")
    @SequenceGenerator(name = "ruleEntitySeq", sequenceName = "SEQ_RULE_ENTITY", allocationSize = 1000)
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    @NotNull
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", nullable = false)
    @NotNull
    private DomainEntity domain;

    @Column(name = "ALIAS")
    @Nullable
    private String alias;

    @Column(name = "VALIDATOR_TYPE")
    @Enumerated(EnumType.STRING)
    @Nullable
    private ValidatorType validatorType;

    @NotNull
    @Column(name = "PATTERN", nullable = false)
    private String pattern;
}
