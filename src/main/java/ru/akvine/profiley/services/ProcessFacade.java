package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.CreateProcess;
import ru.akvine.profiley.services.dto.process.UpdateProcess;

public interface ProcessFacade {
    Process start(String pid, String userUuid);

    Process create(CreateProcess createProcess);

    Process update(UpdateProcess updateProcess);
}
