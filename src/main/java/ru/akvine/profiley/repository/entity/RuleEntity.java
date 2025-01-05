package ru.akvine.profiley.repository.entity;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

@Accessors(chain = true)
@Entity
@Table(name = "RULE_ENTITY")
@NoArgsConstructor
@Setter
@Getter
public class RuleEntity extends BaseEntity<Long> {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruleEntitySeq")
    @SequenceGenerator(name = "ruleEntitySeq", sequenceName = "SEQ_RULE_ENTITY", allocationSize = 1000)
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    @NonNull
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", nullable = false)
    @Nonnull
    private DomainEntity domain;

    @Column(name = "ALIAS")
    @Nullable
    private String alias;

    @NonNull
    @Column(name = "PATTERN", nullable = false)
    private String pattern;
}
