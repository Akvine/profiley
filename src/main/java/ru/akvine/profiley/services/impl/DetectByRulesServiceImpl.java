package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.providers.DomainValidatorsProvider;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.domain.Rule;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DetectByRulesServiceImpl implements DetectByRulesService {
    private final DomainValidatorsProvider domainValidatorsProvider;

    @Override
    public Map<String, Boolean> detect(String value, Collection<Rule> rules) {
        Map<String, Boolean> detectedDomains = new HashMap<>();

        for (Rule rule : rules) {
            if (rule.getPattern().matcher(value).find()) {
                if (rule.getValidatorType() != null) {
                    boolean isCorrect = domainValidatorsProvider
                            .getByType(rule.getValidatorType()).validate(value);
                    detectedDomains.put(rule.getDomain().getName(), isCorrect);
                } else {
                    detectedDomains.put(rule.getDomain().getName(), true);
                }
            }
        }

        return detectedDomains;
    }
}
