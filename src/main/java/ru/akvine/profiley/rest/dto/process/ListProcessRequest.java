package ru.akvine.profiley.rest.dto.process;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.NextPage;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListProcessRequest {
    private List<String> pids;

    @NotNull
    @Valid
    private NextPage nextPage;

    private String state;
}
