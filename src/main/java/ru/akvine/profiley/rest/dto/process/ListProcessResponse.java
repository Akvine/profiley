package ru.akvine.profiley.rest.dto.process;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListProcessResponse extends SuccessfulResponse {
    private List<ProcessDto> processes;
}
