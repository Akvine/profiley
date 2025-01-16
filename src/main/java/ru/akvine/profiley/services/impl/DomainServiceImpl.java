package ru.akvine.profiley.services.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.exceptions.domain.DomainAlreadyExistsException;
import ru.akvine.profiley.exceptions.domain.DomainNotFoundException;
import ru.akvine.profiley.repository.DomainRepository;
import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.services.dto.domain.UpdateDomain;
import ru.akvine.profiley.utils.Asserts;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements DomainService {
    private final static String DEFAULT_UUID_STUB = "DEFAULT_UUID_STUB";

    private final DomainRepository domainRepository;

    private final UserService userService;

    @Override
    @Transactional
    //TODO: переписать на Query DSL или Criteria API
    public List<Domain> get(ListDomains list) {
        Asserts.isNotNull(list, "list is null");
        List<Domain> allUserDomains = domainRepository
                .findAll(list.getUserId())
                .stream()
                .map(Domain::new)
                .toList();

        List<Domain> systemDomains = List.of();
        if (list.isIncludeSystem()) {
            systemDomains = domainRepository.findSystem()
                    .stream().map(Domain::new)
                    .toList();
        }

        return ListUtils.union(allUserDomains, systemDomains);
    }

    @Override
    public Domain create(CreateDomain createDomain) {
        Asserts.isNotNull(createDomain, "createDomain is null");

        String userUuid = createDomain.getUserUuid();
        String name = createDomain.getName();

        UserEntity user = userService.verifyExistsByUuid(userUuid);
        try {
            verifyExistsByNameAndUserUuid(name, userUuid);

            String errorMessage = String.format(
                    "Domain with name = [%s] already exists for user with uuid = [%s]",
                    name, userUuid
            );
            throw new DomainAlreadyExistsException(errorMessage);
        } catch (DomainNotFoundException exception) {
            DomainEntity domainToSave = (DomainEntity) new DomainEntity()
                    .setName(name)
                    .setUser(user)
                    .setUuid(DEFAULT_UUID_STUB);
            return new Domain(domainRepository.save(domainToSave));
        }
    }

    @Override
    public Domain update(UpdateDomain updateDomain) {
        Asserts.isNotNull(updateDomain, "updateDomain is null");

        String domainName = updateDomain.getDomainName();
        String userUuid = updateDomain.getUserUuid();
        String newDomain = updateDomain.getNewDomainName();
        DomainEntity domainToUpdate = verifyExistsByNameAndUserUuid(domainName, userUuid);

        if (StringUtils.isNotBlank(newDomain) && !domainToUpdate.getName().equals(newDomain)) {
            domainToUpdate.setName(newDomain);
            domainToUpdate.setUpdatedDate(new Date());
        }

        return new Domain(domainRepository.save(domainToUpdate));
    }

    @Override
    public void delete(String domainName, String userUuid) {
        Asserts.isNotNull(domainName, "domainName is null");
        Asserts.isNotNull(userUuid, "userUuid is null");

        DomainEntity domain = verifyExistsByNameAndUserUuid(domainName, userUuid);
        domain.setDeleted(true);
        domain.setDeletedDate(new Date());

        domainRepository.save(domain);
    }

    @Override
    public DomainEntity verifyExistsByNameAndUserUuid(String name, String userUuid) {
        Asserts.isNotNull(name, "name is null");
        Asserts.isNotNull(userUuid, "userUuid is null");

        return domainRepository
                .findByNameAndUserUuid(name, userUuid)
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Domain with name = [%s] not found for user with uuid = [%s]",
                            name, userUuid
                    );
                    return new DomainNotFoundException(errorMessage);
                });
    }
}
