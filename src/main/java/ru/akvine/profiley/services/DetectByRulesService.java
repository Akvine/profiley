package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Rule;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DetectByRulesService {
    Map<String, Boolean> detect(String value, Collection<Rule> rules);
}
