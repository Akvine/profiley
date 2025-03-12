package ru.akvine.profiley.rest.dto.process;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.ProcessState;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ProcessDto {
    private String pid;
    private Integer domainsCount;
    private ProcessState state;
    private LocalDateTime startedDate;
    private LocalDateTime completedDate;
    private String fileName;
    private FileExtension fileExtension;
    private String errorMessage;
}
