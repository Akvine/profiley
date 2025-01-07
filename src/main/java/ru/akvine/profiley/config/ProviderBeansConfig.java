package ru.akvine.profiley.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.providers.ProfilerServiceProvider;
import ru.akvine.profiley.services.ProfilerService;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
public class ProviderBeansConfig {
    @Bean
    public ProfilerServiceProvider profilerServiceProvider(List<ProfilerService> profilerServices) {
        Map<FileExtension, ProfilerService> profilers = profilerServices
                .stream()
                .collect(toMap(ProfilerService::getType, identity()));
        return new ProfilerServiceProvider(profilers);
    }
}
