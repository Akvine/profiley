package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.repository.RuleRepository;
import ru.akvine.profiley.services.SystemRuleService;
import ru.akvine.profiley.services.domain.Rule;

import java.util.List;

@RequiredArgsConstructor
public class DbSystemRuleServiceImpl implements SystemRuleService {
    private final RuleRepository ruleRepository;

    @Override
    @Transactional
    public List<Rule> list() {
        return ruleRepository
                .findSystem()
                .stream()
                .map(Rule::new)
                .toList();
    }
}
