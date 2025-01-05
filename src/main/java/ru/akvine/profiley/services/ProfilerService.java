package ru.akvine.profiley.services;

import ru.akvine.profiley.enums.file.FileExtension;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.PossibleDomain;

import java.util.List;

public interface ProfilerService {
    List<? extends PossibleDomain> profile(ProfileAction profileAction);

    FileExtension getType();
}
