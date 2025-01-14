package ru.akvine.profiley.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.ValidatorType;
import ru.akvine.profiley.providers.DomainValidatorsProvider;
import ru.akvine.profiley.providers.ProfilerServiceProvider;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.ProfilerService;
import ru.akvine.profiley.services.validators.DomainValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Bean
    public DomainValidatorsProvider domainValidatorsProvider(List<DomainValidator> domainValidators) {
        Map<ValidatorType, DomainValidator> domainValidatorsMap = domainValidators.stream()
                .collect(toMap(DomainValidator::getType, identity()));
        return new DomainValidatorsProvider(domainValidatorsMap);
    }
}
