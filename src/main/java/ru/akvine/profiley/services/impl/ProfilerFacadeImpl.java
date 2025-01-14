package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.providers.ProfilerServiceProvider;
import ru.akvine.profiley.services.PreprocessorService;
import ru.akvine.profiley.services.ProfilerFacade;
import ru.akvine.profiley.services.dto.PossibleDomain;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfilerFacadeImpl implements ProfilerFacade {
    private final PreprocessorService preprocessorService;
    private final ProfilerServiceProvider profilerServiceProvider;

    @Override
    public List<? extends PossibleDomain> profile(ProfileFile profileFile) {
        ProfileAction profileAction = preprocessorService.preprocess(profileFile);
        return profilerServiceProvider
                .profilerServices()
                .get(profileAction.getExtension())
                .profile(profileAction);
    }
}
