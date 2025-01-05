package ru.akvine.profiley.providers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.akvine.profiley.enums.file.FileExtension;
import ru.akvine.profiley.services.ProfilerService;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ProfilerServiceProvider {
    private final Map<FileExtension, ProfilerService> profilerServices;
}
