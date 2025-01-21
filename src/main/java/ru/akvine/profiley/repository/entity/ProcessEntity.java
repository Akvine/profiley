package ru.akvine.profiley.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.ProcessState;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "PROCESS_ENTITY")
public class ProcessEntity extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processEntitySeq")
    @SequenceGenerator(name = "processEntitySeq", sequenceName = "SEQ_PROCESS_ENTITY", allocationSize = 1000)
    @NotNull
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false)
    private String uuid = "STUB_UUID";

    @Column(name = "PID", nullable = false, updatable = false)
    private String pid;

    @Column(name = "FILE_NAME", nullable = false)
    @NotNull
    private String fileName;

    @Column(name = "FILE_EXTENSION", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private FileExtension fileExtension;

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessState processState;

    @Column(name = "STARTED_DATE")
    private Date startedDate;

    @Column(name = "COMPLETED_DATE")
    private Date completedDate;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;
}
