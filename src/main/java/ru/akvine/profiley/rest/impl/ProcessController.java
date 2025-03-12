package ru.akvine.profiley.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.ProcessControllerMeta;
import ru.akvine.profiley.rest.converter.ProcessConverter;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.process.ListProcessRequest;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.ListProcess;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProcessController implements ProcessControllerMeta {
    private final ProcessService processService;
    private final ProcessConverter processConverter;
    private final SecurityManager securityManager;

    @Override
    public Response get(@PathVariable("pid") String pid) {
        Process process = processService.get(securityManager.getCurrentUser().getUuid(), pid);
        return processConverter.convertToListProcessResponse(List.of(process));
    }

    @Override
    public Response list(@RequestBody @Valid ListProcessRequest request) {
        ListProcess listProcess = processConverter.convertToListProcess(request);
        List<Process> processes = processService.list(listProcess);
        return processConverter.convertToListProcessResponse(processes);
    }
}
