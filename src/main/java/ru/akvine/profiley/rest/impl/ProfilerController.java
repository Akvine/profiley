package ru.akvine.profiley.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.rest.ProfilerControllerMeta;
import ru.akvine.profiley.rest.converter.ProfilerConverter;
import ru.akvine.profiley.rest.validator.ProfilerValidator;
import ru.akvine.profiley.services.ProfilerFacade;
import ru.akvine.profiley.services.dto.PossibleDomain;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfilerController implements ProfilerControllerMeta {
    private final ProfilerValidator profilerValidator;
    private final ProfilerFacade profilerFacade;
    private final ProfilerConverter profilerConverter;

    @Override
    public List<? extends PossibleDomain> profileTextFile(MultipartFile file) {
        profilerValidator.validate(file, FileType.TEXT);
        ProfileFile profileFile = profilerConverter.convertToProfileFile(file);
        return profilerFacade.profile(profileFile);
    }
}
