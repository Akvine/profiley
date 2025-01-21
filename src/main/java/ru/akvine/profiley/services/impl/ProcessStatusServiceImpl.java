package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ProcessState;
import ru.akvine.profiley.exceptions.process.ProcessStartException;
import ru.akvine.profiley.repository.entity.ProcessEntity;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.ProcessStatusService;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.UpdateProcess;
import ru.akvine.profiley.utils.Asserts;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProcessStatusServiceImpl implements ProcessStatusService {
    private final ProcessService processService;

    @Override
    public Process start(String pid, String userUuid) {
        Asserts.isNotNull(userUuid, "userUuid is null");

        ProcessEntity processEntity = processService.verifyExists(pid, userUuid);
        ProcessState currentState = processEntity.getProcessState();

        if (currentState != ProcessState.CREATED) {
            String errorMessage = String.format(
                    "Can't start process with PID = [%s] " +
                            "for user with uuid = [%s]. " +
                            "Process not in the " + ProcessState.CREATED + " state = [%s]",
                    pid, userUuid, currentState
            );
            throw new ProcessStartException(errorMessage);
        }

        UpdateProcess updateProcess = new UpdateProcess()
                .setPid(processEntity.getPid())
                .setUserUuid(processEntity.getUser().getUuid())
                .setProcessState(ProcessState.STARTED)
                .setStartedDate(new Date());
        return processService.update(updateProcess);
    }
}
