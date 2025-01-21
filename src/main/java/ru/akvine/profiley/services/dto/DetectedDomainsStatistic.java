package ru.akvine.profiley.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.ProcessState;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class DetectedDomainsStatistic {
    private String pid;
    private int domainsCount;
    private ProcessState processState;
    private LocalDateTime startedDate;
    private LocalDateTime completedDate;
}
