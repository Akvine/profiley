package ru.akvine.profiley.services;

import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.repository.entity.RuleEntity;
import ru.akvine.profiley.rest.dto.rule.ListRules;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.rule.CreateRule;
import ru.akvine.profiley.services.dto.rule.DeleteRule;
import ru.akvine.profiley.services.dto.rule.UpdateRule;

import java.util.List;

public interface RuleService {
    @Transactional
    List<Rule> getSystem();

    @Transactional
    List<Rule> get(long userId);

    @Transactional
    List<Rule> get(ListRules listRules);

    @Transactional
    Rule create(CreateRule createRule);

    @Transactional
    Rule update(UpdateRule updateRule);

    void delete(DeleteRule deleteRule);

    RuleEntity verifyExistsByUuidAndUserUuid(String uuid, String userUuid);
}
