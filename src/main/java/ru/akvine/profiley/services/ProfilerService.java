package ru.akvine.profiley.services;

import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;
import ru.akvine.profiley.services.dto.ProfileAction;

import java.util.List;

public interface ProfilerService {
    List<? extends DetectedDomain> profile(ProfileAction profileAction);

    FileType getType();
}
