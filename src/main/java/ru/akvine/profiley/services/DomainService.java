package ru.akvine.profiley.services;

import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;

import java.util.List;

public interface DomainService {
    List<Domain> get(ListDomains list);

    Domain create(CreateDomain createDomain);

    DomainEntity verifyExistsByNameAndUserUuid(String name, String userUuid);
}
