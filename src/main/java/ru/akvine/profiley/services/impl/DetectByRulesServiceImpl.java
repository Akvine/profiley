package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.providers.DomainValidatorsProvider;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.domain.Rule;

import java.util.*;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class DetectByRulesServiceImpl implements DetectByRulesService {
    private final DomainValidatorsProvider domainValidatorsProvider;

    @Override
    public Map<String, Boolean> detect(String row, Collection<Rule> rules) {
        Map<String, Boolean> detectedDomains = new HashMap<>();

        for (Rule rule : rules) {
            Matcher matcher = rule.getPattern().matcher(row);
            while (matcher.find()) {
                if (rule.getValidatorType() != null) {
                    boolean isCorrect = domainValidatorsProvider
                            .getByType(rule.getValidatorType()).validate(matcher.group());
                    detectedDomains.put(rule.getDomain().getName(), isCorrect);
                } else {
                    detectedDomains.put(rule.getDomain().getName(), true);
                }
            }
        }

        return detectedDomains;
    }
}
