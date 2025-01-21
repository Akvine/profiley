package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.providers.DomainValidatorsProvider;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.RuleInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class DetectByRulesServiceImpl implements DetectByRulesService {
    private final DomainValidatorsProvider domainValidatorsProvider;

    @Override
    public List<RuleInfo> detect(String row, Collection<Rule> rules) {
        List<RuleInfo> detectedDomains = new ArrayList<>();

        for (Rule rule : rules) {
            Matcher matcher = rule.getPattern().matcher(row);
            while (matcher.find()) {
                if (rule.getValidatorType() != null) {
                    boolean isCorrect = domainValidatorsProvider
                            .getByType(rule.getValidatorType()).validate(matcher.group());
                    detectedDomains.add(new RuleInfo()
                            .setDomainName(rule.getDomain().getName())
                            .setValue(matcher.group())
                            .setCorrect(isCorrect));
                } else {
                    detectedDomains.add(new RuleInfo()
                            .setDomainName(rule.getDomain().getName())
                            .setValue(matcher.group())
                            .setCorrect(true));
                }
            }
        }

        return detectedDomains;
    }
}
