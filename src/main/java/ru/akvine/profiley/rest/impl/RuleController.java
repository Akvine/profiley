package ru.akvine.profiley.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.rest.RuleControllerMeta;
import ru.akvine.profiley.rest.converter.RuleConverter;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.rule.CreateRuleRequest;
import ru.akvine.profiley.rest.dto.rule.ListRules;
import ru.akvine.profiley.services.RuleService;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.CreateRule;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RuleController implements RuleControllerMeta {
    private final RuleConverter ruleConverter;
    private final RuleService ruleService;

    @Override
    public Response list(@RequestParam("domainName") String domainName) {
        ListRules listRules = ruleConverter.convertTolistRules(domainName);
        List<Rule> rules = ruleService.get(listRules);
        return ruleConverter.convertToListRuleResponse(rules);
    }

    @Override
    public Response create(@Valid CreateRuleRequest request) {
        CreateRule createRule = ruleConverter.convertToCreateRule(request);
        Rule createdRule = ruleService.create(createRule);
        return ruleConverter.convertToListRuleResponse(List.of(createdRule));
    }
}
