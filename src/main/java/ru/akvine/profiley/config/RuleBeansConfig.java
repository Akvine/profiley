package ru.akvine.profiley.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akvine.profiley.repository.RuleRepository;
import ru.akvine.profiley.services.SystemRuleService;
import ru.akvine.profiley.services.impl.CacheSystemRuleServiceImpl;
import ru.akvine.profiley.services.impl.DbSystemRuleServiceImpl;

@Configuration
@RequiredArgsConstructor
public class RuleBeansConfig {
    private final RuleRepository ruleRepository;

    @Bean
    public SystemRuleService systemRuleService(@Value("${system.rules.local.cache.enabled}") boolean systemRulesCacheEnabled) {
        if (systemRulesCacheEnabled) {
            return new CacheSystemRuleServiceImpl(ruleRepository);
        } else {
            return new DbSystemRuleServiceImpl(ruleRepository);
        }
    }
}
