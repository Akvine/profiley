package ru.akvine.profiley.providers;

import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.ProfilerService;

import java.util.Map;

public record ProfilerServiceProvider(Map<FileExtension, ProfilerService> profilerServices) {
}
