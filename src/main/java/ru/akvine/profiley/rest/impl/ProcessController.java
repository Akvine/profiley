package ru.akvine.profiley.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.ProcessControllerMeta;
import ru.akvine.profiley.rest.converter.ProcessConverter;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.domain.Process;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProcessController implements ProcessControllerMeta {
    private final ProcessService processService;
    private final ProcessConverter processConverter;
    private final SecurityManager securityManager;

    @Override
    public Response list() {
        List<Process> processes = processService.list(securityManager.getCurrentUser().getUuid());
        return processConverter.convertToListProcessResponse(processes);
    }
}
