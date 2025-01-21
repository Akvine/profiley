package ru.akvine.profiley.services.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.exceptions.rule.RuleNotFoundException;
import ru.akvine.profiley.repository.RuleRepository;
import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.repository.entity.RuleEntity;
import ru.akvine.profiley.rest.dto.rule.ListRules;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.UserRuleService;
import ru.akvine.profiley.services.SystemRuleService;
import ru.akvine.profiley.services.domain.domain.Domain;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.services.dto.rule.CreateRule;
import ru.akvine.profiley.services.dto.rule.DeleteRule;
import ru.akvine.profiley.services.dto.rule.UpdateRule;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.generators.UUIDGenerator;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRuleServiceImpl implements UserRuleService {
    private final RuleRepository ruleRepository;

    private final DomainService domainService;
    private final SecurityManager securityManager;
    private final SystemRuleService systemRuleService;

    @Override
    public List<Rule> get(String userUuid) {
        ListDomains listDomains = new ListDomains()
                .setUserUuid(userUuid);
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
        Asserts.isNotNull(listRules, "listRules is null");

        List<Rule> rulesBySystemDomains = List.of();
        List<Rule> userRules = List.of();

        if (listRules.isIncludeSystemDomains()) {
            rulesBySystemDomains = systemRuleService.list();
        }

        if (StringUtils.isNotBlank(listRules.getDomainName())) {
            userRules = ruleRepository
                    .findBy(listRules.getDomainName(), listRules.getUserUuid())
                    .stream()
                    .map(Rule::new)
                    .toList();
        }

        return ListUtils.union(rulesBySystemDomains, userRules);
    }

    @Override
    public Rule create(CreateRule createRule) {
        Asserts.isNotNull(createRule, "createRule is null");

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

    @Override
    public Rule update(UpdateRule updateRule) {
        Asserts.isNotNull(updateRule, "updateRule is null");

        String uuid = updateRule.getUuid();
        String userUuid = updateRule.getUserUuid();
        String pattern = updateRule.getPattern();
        String alias = updateRule.getAlias();
        String domainName = updateRule.getDomainName();

        RuleEntity ruleToUpdate = verifyExistsByUuidAndUserUuid(uuid, userUuid);

        if (StringUtils.isNotBlank(pattern) &&
                !ruleToUpdate.getPattern().equals(pattern)) {
            ruleToUpdate.setPattern(pattern);
        }

        if (StringUtils.isNotBlank(alias) &&
                !ruleToUpdate.getAlias().equals(alias)) {
            ruleToUpdate.setAlias(alias);
        }

        if (StringUtils.isNotBlank(domainName) && !ruleToUpdate.getDomain().getName().equals(domainName)) {
            DomainEntity newDomain = domainService.verifyExistsByNameAndUserUuid(domainName, userUuid);
            ruleToUpdate.setDomain(newDomain);
        }

        ruleToUpdate.setUpdatedDate(new Date());
        return new Rule(ruleRepository.save(ruleToUpdate));
    }

    @Override
    public void delete(DeleteRule deleteRule) {
        Asserts.isNotNull(deleteRule, "deleteRule is null");

        String uuid = deleteRule.getUuid();
        String userUuid = deleteRule.getUserUuid();
        RuleEntity ruleToDelete = verifyExistsByUuidAndUserUuid(uuid, userUuid);

        ruleToDelete.setDeleted(true);
        ruleToDelete.setDeletedDate(new Date());
        ruleRepository.save(ruleToDelete);
    }

    @Override
    public RuleEntity verifyExistsByUuidAndUserUuid(String uuid, String userUuid) {
        return ruleRepository.findByUuid(uuid, userUuid)
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Rule with uuid = [%s] for user with uuid = [%s] not found!",
                            uuid, userUuid
                    );
                    return new RuleNotFoundException(errorMessage);
                });
    }

}
