package ru.akvine.profiley.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.akvine.profiley.enums.Language;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "USER_ENTITY")
public class UserEntity extends BaseEntity<Long> implements UserDetails {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userEntitySeq")
    @SequenceGenerator(name = "userEntitySeq", sequenceName = "SEQ_USER_ENTITY", allocationSize = 1000)
    @NotNull
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    @NotNull
    private String uuid;

    @Column(name = "EMAIL", nullable = false)
    @NotNull
    private String email;

    @Column(name = "HASH", nullable = false)
    @NotNull
    private String hash;

    @Column(name = "IS_DISABLED_SYSTEM_DOMAINS", nullable = false)
    private boolean disabledSystemDomains;

    @Column(name = "IS_DISABLED_SYSTEM_RULES", nullable = false)
    private boolean disabledSystemRules;

    @Column(name = "LANGUAGE", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Language language;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
