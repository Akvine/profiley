package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Rule;

import java.util.Collection;
import java.util.List;

public interface DetectByRulesService {
    List<String> detect(String value, Collection<Rule> rules);
}
