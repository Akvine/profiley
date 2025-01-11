package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Rule;

import java.util.List;

public interface SystemRuleService {
    List<Rule> list();
}
