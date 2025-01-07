package ru.akvine.profiley.rest.converter;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.rule.*;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.CreateRule;
import ru.akvine.profiley.services.dto.rule.DeleteRule;
import ru.akvine.profiley.services.dto.rule.UpdateRule;
import ru.akvine.profiley.utils.StringHelper;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RuleConverter {
    private final SecurityManager securityManager;

    @Value("${system.rules.masking.enabled}")
    private boolean systemRulesMaskingEnabled;
    @Value("${system.rules.masking.symbol}")
    private char systemRulesMaskingSymbol;

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
        RuleDto ruleDto = new RuleDto()
                .setUuid(rule.getUuid())
                .setAlias(rule.getAlias())
                .setDomainName(rule.getDomain().getName());

        if (systemRulesMaskingEnabled) {
            String originPattern = rule.getPattern().toString();
            int radius = originPattern.length() / 4;
            String maskedPattern = StringHelper.replaceAroundMiddle(
                    originPattern,
                    systemRulesMaskingSymbol,
                    radius);
            ruleDto.setPattern(maskedPattern);
        } else {
            ruleDto.setPattern(rule.getPattern().toString());
        }

        return ruleDto;

    }
}
