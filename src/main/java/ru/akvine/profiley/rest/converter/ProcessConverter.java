package ru.akvine.profiley.rest.converter;

import org.springframework.stereotype.Component;
import ru.akvine.profiley.rest.dto.process.ListProcessResponse;
import ru.akvine.profiley.rest.dto.process.ProcessDto;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.DateTimeUtils;

import java.util.List;

@Component
public class ProcessConverter {
    public ListProcessResponse convertToListProcessResponse(List<Process> processes) {
        Asserts.isNotNull(processes, "processes is null");
        return new ListProcessResponse().setProcesses(processes.stream().map(this::buildProcessDto).toList());
    }

    private ProcessDto buildProcessDto(Process process) {
        return new ProcessDto()
                .setPid(process.getPid())
                .setFileName(process.getFileName())
                .setFileExtension(process.getFileExtension())
                .setState(process.getProcessState())
                .setStartedDate(process.getStartedDate() != null ? DateTimeUtils.toLocalDateTime(process.getStartedDate()) : null)
                .setCompletedDate(process.getCompletedDate() != null ? DateTimeUtils.toLocalDateTime(process.getCompletedDate()) : null);
    }
}
