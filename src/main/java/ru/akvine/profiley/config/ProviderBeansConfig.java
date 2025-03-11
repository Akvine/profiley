package ru.akvine.profiley.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.enums.ValidatorType;
import ru.akvine.profiley.providers.DetectedDomainsServicesProvider;
import ru.akvine.profiley.providers.DomainValidatorsProvider;
import ru.akvine.profiley.providers.ProfilerServiceProvider;
import ru.akvine.profiley.providers.ReportGeneratorsProvider;
import ru.akvine.profiley.services.DetectedDomainService;
import ru.akvine.profiley.services.ProfilerService;
import ru.akvine.profiley.services.report.ReportGenerator;
import ru.akvine.profiley.services.validators.DomainValidator;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
public class ProviderBeansConfig {
    @Bean
    public ProfilerServiceProvider profilerServiceProvider(List<ProfilerService> profilerServices) {
        Map<FileType, ProfilerService> profilers = profilerServices
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

    @Bean
    public DetectedDomainsServicesProvider detectedDomainsServicesProvider(List<DetectedDomainService<?>> detectedDomainServices) {
        Map<FileType, DetectedDomainService<?>> detectedDomainServiceMap = detectedDomainServices.stream()
                .collect(toMap(DetectedDomainService::getType, identity()));
        return new DetectedDomainsServicesProvider(detectedDomainServiceMap);
    }

    @Bean
    public ReportGeneratorsProvider reportGeneratorsProvider(List<ReportGenerator> reportGenerators) {
        Map<FileType, ReportGenerator> reportGeneratorMap = reportGenerators
                .stream()
                .collect(toMap(ReportGenerator::getType, identity()));
        return new ReportGeneratorsProvider(reportGeneratorMap);
    }
}
