package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.repository.RuleRepository;
import ru.akvine.profiley.services.SystemRuleService;
import ru.akvine.profiley.services.domain.Rule;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CacheSystemRuleServiceImpl implements SystemRuleService, ApplicationListener<ApplicationReadyEvent> {
    private final RuleRepository ruleRepository;

    private Set<Rule> CACHE_RULES;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.debug("Cache system rules service bean initializing...");

        CACHE_RULES = ruleRepository
                .findSystem()
                .stream()
                .map(Rule::new)
                .collect(Collectors.toSet());

        logger.debug("Cache system rules service bean initialized successful");
    }

    @Override
    public List<Rule> list() {
        return CACHE_RULES.stream().toList();
    }


}
