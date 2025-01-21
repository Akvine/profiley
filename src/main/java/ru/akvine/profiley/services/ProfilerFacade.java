package ru.akvine.profiley.services;

import ru.akvine.profiley.services.dto.DetectedDomainsStatistic;
import ru.akvine.profiley.services.dto.ProfileFile;

public interface ProfilerFacade {
    DetectedDomainsStatistic profile(ProfileFile profileFile);
}
