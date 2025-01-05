package ru.akvine.profiley.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

@Accessors(chain = true)
@Entity
@Table(name = "DOMAIN_ENTITY")
@NoArgsConstructor
@Setter
@Getter
public class DomainEntity extends BaseEntity<Long> {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domainEntitySeq")
    @SequenceGenerator(name = "domainEntitySeq", sequenceName = "SEQ_DOMAIN_ENTITY", allocationSize = 1000)
    @NonNull
    private Long id;

    @NonNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Transient
    public boolean isSystem() {
        return user == null;
    }
}
