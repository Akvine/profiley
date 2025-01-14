package ru.akvine.profiley.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "DICTIONARY_ENTITY")
public class DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dictionaryEntitySeq")
    @SequenceGenerator(name = "dictionaryEntitySeq", sequenceName = "SEQ_DICTIONARY_ENTITY", allocationSize = 1000)
    @NonNull
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    @NonNull
    private String uuid;

    @Column(name = "WORDS", nullable = false)
    @NonNull
    private String words;

    @Column(name = "SEPARATOR", nullable = false)
    @NonNull
    private String separator;

    @Column(name = "LOCALE")
    private String locale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", nullable = false)
    @NonNull
    private DomainEntity domain;

    @Column(name = "CREATED_DATE", nullable = false)
    @NonNull
    private Date createdDate = new Date();

    @Column(name = "UPDATED_DATE")
    @Nullable
    private Date updatedDate;
}
