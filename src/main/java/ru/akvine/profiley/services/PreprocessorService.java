package ru.akvine.profiley.services;

import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;

public interface PreprocessorService {
    ProfileAction preprocess(ProfileFile profileFile);
}
