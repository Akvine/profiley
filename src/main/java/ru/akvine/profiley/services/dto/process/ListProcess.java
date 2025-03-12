package ru.akvine.profiley.services.dto.process;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.ProcessState;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListProcess {
    private String userUuid;

    private Integer page;
    private Integer size;

    @Nullable
    private List<String> pids;

    @Nullable
    private ProcessState state;
}
