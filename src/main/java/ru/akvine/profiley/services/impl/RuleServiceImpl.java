package ru.akvine.profiley.services.impl;

import com.google.common.base.Preconditions;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.exceptions.RuleNotFoundException;
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
import ru.akvine.profiley.services.dto.rule.DeleteRule;
import ru.akvine.profiley.services.dto.rule.UpdateRule;
import ru.akvine.profiley.utils.UUIDGenerator;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {
    private final RuleRepository ruleRepository;

    private final DomainService domainService;
    private final SecurityManager securityManager;

    @Override
    public List<Rule> getSystem() {
        return ruleRepository
                .findSystem()
                .stream()
                .map(Rule::new)
                .toList();
    }

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

        List<Rule> rulesBySystemDomains = List.of();
        List<Rule> userRules = List.of();

        if (listRules.isIncludeSystemDomains()) {
            rulesBySystemDomains = ruleRepository.findSystem().stream().map(Rule::new).toList();
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

    @Override
    public Rule update(UpdateRule updateRule) {
        Preconditions.checkNotNull(updateRule, "updateRule is null");

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
        Preconditions.checkNotNull(deleteRule, "deleteRule is null");

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
