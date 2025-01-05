package ru.akvine.profiley.services;

import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.rest.dto.rule.ListRules;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.CreateRule;

import java.util.List;

public interface RuleService {
    @Transactional
    List<Rule> get(long userId);

    @Transactional
    List<Rule> get(ListRules listRules);

    @Transactional
    Rule create(CreateRule createRule);
}
