package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.DetectByRulesService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetectByRulesServiceImpl implements DetectByRulesService {

    @Override
    public List<String> detect(String value, Collection<Rule> rules) {
        List<String> detectedDomains = new ArrayList<>();

        for (Rule rule : rules) {
            if (rule.getPattern().matcher(value).find()) {
                detectedDomains.add(rule.getDomain().getName());
            }
        }

        return detectedDomains;
    }
}
