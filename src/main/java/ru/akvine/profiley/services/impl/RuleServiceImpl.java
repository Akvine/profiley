package ru.akvine.profiley.services.impl;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.repository.RuleRepository;
import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.repository.entity.RuleEntity;
import ru.akvine.profiley.rest.dto.rule.ListRules;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.RuleService;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.services.dto.rule.CreateRule;
import ru.akvine.profiley.utils.UUIDGenerator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {
    private final RuleRepository ruleRepository;

    private final DomainService domainService;
    private final SecurityManager securityManager;

    @Override
    public List<Rule> get(long userId) {
        ListDomains listDomains = new ListDomains()
                .setUserId(userId)
                .setIncludeSystem(true);
        List<Domain> domains = domainService.get(listDomains);
        List<Long> ids = domains
                .stream()
                .map(Domain::getId)
                .toList();
        return ruleRepository
                .findAll(ids)
                .stream()
                .map(Rule::new)
                .toList();
    }

    @Override
    public List<Rule> get(ListRules listRules) {
        Preconditions.checkNotNull(listRules, "listRules is null");
        return ruleRepository
                .findBy(listRules.getDomainName(), listRules.getUserUuid())
                .stream()
                .map(Rule::new)
                .toList();
    }

    @Override
    public Rule create(CreateRule createRule) {
        Preconditions.checkNotNull(createRule, "createRule is null");

        String domainName = createRule.getDomainName();
        DomainEntity domain = domainService.verifyExistsByNameAndUserUuid(
                domainName,
                securityManager.getCurrentUser().getUuid()
        );

        RuleEntity ruleToCreate = new RuleEntity()
                .setUuid(UUIDGenerator.uuidWithoutDashes())
                .setDomain(domain)
                .setAlias(createRule.getAlias())
                .setPattern(createRule.getPattern());
        return new Rule(ruleRepository.save(ruleToCreate));
    }

}
