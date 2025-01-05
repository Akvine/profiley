package ru.akvine.profiley.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    @NonNull
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    private String uuid;

    @Column(name = "EMAIL", nullable = false)
    @NonNull
    private String email;

    @Column(name = "HASH", nullable = false)
    @NonNull
    private String hash;

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
