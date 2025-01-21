package ru.akvine.profiley.services.dto.process;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.ProcessState;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UpdateProcess {
    private String userUuid;
    private String pid;

    @Nullable
    private ProcessState processState;
    @Nullable
    private String errorMessage;
    @Nullable
    private Date startedDate;
    @Nullable
    private Date completedDate;
}
