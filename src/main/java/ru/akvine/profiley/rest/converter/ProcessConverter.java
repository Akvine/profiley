package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.enums.ProcessState;
import ru.akvine.profiley.rest.dto.process.ListProcessRequest;
import ru.akvine.profiley.rest.dto.process.ListProcessResponse;
import ru.akvine.profiley.rest.dto.process.ProcessDto;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.ListProcess;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.DateTimeUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessConverter {
    private final SecurityManager securityManager;

    public ListProcess convertToListProcess(ListProcessRequest request) {
        Asserts.isNotNull(request, "listProcessRequest is null");
        return new ListProcess()
                .setPids(request.getPids())
                .setState(StringUtils.isBlank(request.getState()) ? null : ProcessState.from(request.getState()))
                .setPage(request.getNextPage().getPage())
                .setSize(request.getNextPage().getSize())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

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
