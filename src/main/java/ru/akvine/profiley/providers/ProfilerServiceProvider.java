package ru.akvine.profiley.providers;

import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.services.ProfilerService;

import java.util.Map;

public record ProfilerServiceProvider(Map<FileType, ProfilerService> profilerServices) {
}
