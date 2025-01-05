package ru.akvine.profiley.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity<ID> {
    @Column(name = "UUID", updatable = false, nullable = false)
    @NonNull
    private String uuid;

    @Column(name = "CREATED_DATE", nullable = false)
    @NonNull
    private Date createdDate = new Date();

    @Column(name = "UPDATED_DATE")
    @Nullable
    private Date updatedDate;

    @Column(name = "DELETED_DATE")
    @Nullable
    private Date deletedDate;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean deleted;

    public abstract ID getId();

    public void deleteRelative(Date deletedDate) {}
}
