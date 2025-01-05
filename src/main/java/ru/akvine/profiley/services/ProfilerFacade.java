package ru.akvine.profiley.services;

import ru.akvine.profiley.services.dto.PossibleDomain;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.util.List;

public interface ProfilerFacade {
    List<? extends PossibleDomain> profile(ProfileFile profileFile);
}
