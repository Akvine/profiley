package ru.akvine.profiley.services;

import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.repository.entity.domain.DomainEntity;
import ru.akvine.profiley.services.domain.domain.Domain;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.services.dto.domain.UpdateDomain;

import java.util.List;

public interface DomainService {
    List<Domain> get(ListDomains list);

    Domain create(CreateDomain createDomain);

    @Transactional
    Domain update(UpdateDomain updateDomain);

    void delete(String domainName, String userUuid);

    DomainEntity verifyExistsByNameAndUserUuid(String name, String userUuid);
}
