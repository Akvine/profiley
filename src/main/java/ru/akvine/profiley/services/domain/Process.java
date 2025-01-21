package ru.akvine.profiley.services.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.ProcessState;
import ru.akvine.profiley.repository.entity.ProcessEntity;
import ru.akvine.profiley.services.domain.base.Model;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Process extends Model<Long> {
    private String pid;
    private String fileName;
    private FileExtension fileExtension;
    private ProcessState processState;
    private Date startedDate;
    @Nullable
    private Date completedDate;
    private User owner;
    @Nullable
    private String errorMessage;

    public Process(ProcessEntity processEntity) {
        super(processEntity);

        this.pid = processEntity.getPid();
        this.fileName = processEntity.getFileName();
        this.fileExtension = processEntity.getFileExtension();
        this.processState = processEntity.getProcessState();
        this.startedDate = processEntity.getStartedDate();
        this.completedDate = processEntity.getCompletedDate();
        this.errorMessage = processEntity.getErrorMessage();
        this.owner = new User(processEntity.getUser());
    }
}
