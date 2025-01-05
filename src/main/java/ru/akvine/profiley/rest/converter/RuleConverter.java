package ru.akvine.profiley.rest.converter;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.rule.CreateRuleRequest;
import ru.akvine.profiley.rest.dto.rule.ListRuleResponse;
import ru.akvine.profiley.rest.dto.rule.ListRules;
import ru.akvine.profiley.rest.dto.rule.RuleDto;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.CreateRule;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RuleConverter {
    private final SecurityManager securityManager;

    public ListRules convertTolistRules(String domainName) {
        Preconditions.checkNotNull(domainName, "domainName is null");
        return new ListRules()
                .setDomainName(domainName)
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public CreateRule convertToCreateRule(CreateRuleRequest request) {
        Preconditions.checkNotNull(request, "createRuleRequest is null");
        return new CreateRule()
                .setAlias(request.getAlias())
                .setPattern(request.getPattern())
                .setDomainName(request.getDomainName());
    }

    public ListRuleResponse convertToListRuleResponse(Collection<Rule> rulesToConvert) {
        return new ListRuleResponse().setRules(rulesToConvert.stream().map(this::buildRuleDto).toList());
    }

    private RuleDto buildRuleDto(Rule rule) {
        return new RuleDto()
                .setUuid(rule.getUuid())
                .setAlias(rule.getAlias())
                .setPattern(rule.getPattern().toString());
    }
}
