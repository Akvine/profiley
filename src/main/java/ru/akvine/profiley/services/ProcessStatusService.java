package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Process;

public interface ProcessStatusService {
    Process start(String pid, String userUuid);
}
