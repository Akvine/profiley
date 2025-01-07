package ru.akvine.profiley.rest.converter;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.rule.*;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.CreateRule;
import ru.akvine.profiley.services.dto.rule.DeleteRule;
import ru.akvine.profiley.services.dto.rule.UpdateRule;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RuleConverter {
    private final SecurityManager securityManager;

    public ListRules convertTolistRules(ListRuleRequest request) {
        Preconditions.checkNotNull(request, "listRuleRequest is null");
        return new ListRules()
                .setIncludeSystemDomains(request.isIncludeSystemDomains())
                .setDomainName(request.getDomainName())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public CreateRule convertToCreateRule(CreateRuleRequest request) {
        Preconditions.checkNotNull(request, "createRuleRequest is null");
        return new CreateRule()
                .setAlias(request.getAlias())
                .setPattern(request.getPattern())
                .setDomainName(request.getDomainName());
    }

    public UpdateRule convertToUpdateRule(UpdateRuleRequest request) {
        Preconditions.checkNotNull(request, "updateRuleRequest is null");
        return new UpdateRule()
                .setAlias(request.getAlias())
                .setUuid(request.getUuid())
                .setPattern(request.getPattern())
                .setUserUuid(securityManager.getCurrentUser().getUuid())
                .setDomainName(request.getDomainName());
    }

    public DeleteRule convertToDeleteRule(String uuid) {
        Preconditions.checkNotNull(uuid, "uuid is null");
        return new DeleteRule()
                .setUuid(uuid)
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public ListRuleResponse convertToListRuleResponse(Collection<Rule> rulesToConvert) {
        return new ListRuleResponse().setRules(rulesToConvert.stream().map(this::buildRuleDto).toList());
    }

    private RuleDto buildRuleDto(Rule rule) {
        return new RuleDto()
                .setUuid(rule.getUuid())
                .setAlias(rule.getAlias())
                .setPattern(rule.getPattern().toString())
                .setDomainName(rule.getDomain().getName());
    }
}
