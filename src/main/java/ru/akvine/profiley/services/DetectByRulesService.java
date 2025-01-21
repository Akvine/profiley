package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.RuleInfo;

import java.util.Collection;
import java.util.List;

public interface DetectByRulesService {
    List<RuleInfo> detect(String row, Collection<Rule> rules);
}
