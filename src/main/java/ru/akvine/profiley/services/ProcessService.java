package ru.akvine.profiley.services;

import ru.akvine.profiley.repository.entity.ProcessEntity;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.CreateProcess;
import ru.akvine.profiley.services.dto.process.ListProcess;
import ru.akvine.profiley.services.dto.process.UpdateProcess;

import java.util.List;

public interface ProcessService {
    Process get(String userUuid, String pid);

    List<Process> list(ListProcess listProcess);

    Process create(CreateProcess createProcess);

    Process update(UpdateProcess updateProcess);

    ProcessEntity verifyExists(String byPid, String byUserUuid);
}
