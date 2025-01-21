package ru.akvine.profiley.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
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
    @NotNull
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    @NotNull
    private String uuid;

    @Column(name = "WORDS", nullable = false)
    @NotNull
    private String words;

    @Column(name = "SEPARATOR", nullable = false)
    @NotNull
    private String separator;

    @Column(name = "LOCALE")
    private String locale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", nullable = false)
    @NotNull
    private ru.akvine.profiley.repository.entity.DomainEntity domain;

    @Column(name = "CREATED_DATE", nullable = false)
    @NotNull
    private Date createdDate = new Date();

    @Column(name = "UPDATED_DATE")
    @Nullable
    private Date updatedDate;
}
