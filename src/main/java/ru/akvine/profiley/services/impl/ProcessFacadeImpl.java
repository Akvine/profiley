package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.ProcessFacade;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.ProcessStatusService;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.CreateProcess;
import ru.akvine.profiley.services.dto.process.UpdateProcess;
import ru.akvine.profiley.utils.Asserts;

@Service
@RequiredArgsConstructor
public class ProcessFacadeImpl implements ProcessFacade {
    private final ProcessService processService;
    private final ProcessStatusService processStatusService;

    @Override
    public Process start(String pid, String userUuid) {
        Asserts.isNotNull(pid, "userUuid is null");
        return processStatusService.start(pid, userUuid);
    }

    @Override
    public Process update(UpdateProcess updateProcess) {
        Asserts.isNotNull(updateProcess, "updateProcess is null");
        return processService.update(updateProcess);
    }

    @Override
    public Process create(CreateProcess createProcess) {
        return processService.create(createProcess);
    }
}
