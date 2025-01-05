package ru.akvine.profiley.services.impl;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.exceptions.DomainAlreadyExistsException;
import ru.akvine.profiley.exceptions.DomainNotFoundException;
import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.repository.DomainRepository;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;

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
        Preconditions.checkNotNull(list, "list is null");
        List<Domain> allUserDomains = domainRepository
                .findAll(list.getUserId())
                .stream()
                .map(Domain::new)
                .toList();

        if (list.isIncludeSystem()) {
            return allUserDomains;
        } else {
            return allUserDomains.stream()
                    .filter(domain -> !domain.isSystem())
                    .toList();
        }
    }

    @Override
    public Domain create(CreateDomain createDomain) {
        Preconditions.checkNotNull(createDomain, "createDomain is null");

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
    public DomainEntity verifyExistsByNameAndUserUuid(String name, String userUuid) {
        Preconditions.checkNotNull(name, "name is null");
        Preconditions.checkNotNull(userUuid, "userUuid is null");

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
