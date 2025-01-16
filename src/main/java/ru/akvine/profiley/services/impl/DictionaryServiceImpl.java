package ru.akvine.profiley.services.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.exceptions.DictionaryMaxCountException;
import ru.akvine.profiley.exceptions.DictionaryNotFoundException;
import ru.akvine.profiley.repository.DictionaryRepository;
import ru.akvine.profiley.repository.entity.DictionaryEntity;
import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.services.DictionaryService;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.dto.dictionary.CreateDictionary;
import ru.akvine.profiley.services.dto.dictionary.UpdateDictionary;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.UUIDGenerator;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    private final DomainService domainService;

    @Value("${max.dictionaries.count.per.user}")
    private int maxDictionariesCount;

    @Override
    public List<Dictionary> list(long userId) {
        ListDomains listDomains = new ListDomains()
                .setUserId(userId)
                .setIncludeSystem(true);
        List<Domain> domains = domainService.get(listDomains);
        List<Long> ids = domains.stream().map(Domain::getId).toList();
        return dictionaryRepository
                .findAll(ids)
                .stream()
                .map(Dictionary::new)
                .toList();
    }

    @Override
    public Dictionary create(CreateDictionary createDictionary) {
        Asserts.isNotNull(createDictionary, "createDictionary is null");

        String userUuid = createDictionary.getUserUuid();
        String domainName = createDictionary.getDomainName();
        String separator = createDictionary.getSeparator();
        String locale = createDictionary.getLocale();

        long createdCount = dictionaryRepository.count(userUuid);
        if (createdCount == maxDictionariesCount) {
            String errorMessage = String.format("Can't create dictionary! Max count is reached = [%s]", maxDictionariesCount);
            throw new DictionaryMaxCountException(errorMessage);
        }

        DomainEntity domain = domainService.verifyExistsByNameAndUserUuid(domainName, userUuid);
        DictionaryEntity dictionaryToCreate = new DictionaryEntity()
                .setDomain(domain)
                .setCreatedDate(new Date())
                .setSeparator(separator)
                .setLocale(locale)
                .setWords(String.join(separator, createDictionary.getWords()))
                .setUuid(UUIDGenerator.uuid());

        return new Dictionary(dictionaryRepository.save(dictionaryToCreate));
    }

    @Override
    public Dictionary update(UpdateDictionary updateDictionary) {
        Asserts.isNotNull(updateDictionary, "updateDictionary is null");

        String uuid = updateDictionary.getUuid();
        String userUuid = updateDictionary.getUserUuid();

        String domainName = updateDictionary.getDomainName();
        String locale = updateDictionary.getLocale();

        DictionaryEntity dictionaryToUpdate = verifyExistsByUuidAndUserUuid(uuid, userUuid);
        if (StringUtils.isNotBlank(domainName) &&
                !dictionaryToUpdate.getDomain().getName().equals(domainName)) {
            DomainEntity domain = domainService.verifyExistsByNameAndUserUuid(domainName, userUuid);
            dictionaryToUpdate.setDomain(domain);
        }

        if (CollectionUtils.isNotEmpty(updateDictionary.getWords())) {
            dictionaryToUpdate.setWords(
                    String.join(dictionaryToUpdate.getSeparator(), updateDictionary.getWords())
            );
        }

        if (StringUtils.isNotBlank(locale) &&
                (dictionaryToUpdate.getLocale() == null ||
                        !dictionaryToUpdate.getLocale().equals(locale))) {
            dictionaryToUpdate.setLocale(locale);
        }

        return new Dictionary(dictionaryRepository.save(dictionaryToUpdate));
    }

    @Override
    public void delete(String uuid, String userUuid) {
        Asserts.isNotNull(uuid, "uuid is null");
        Asserts.isNotNull(userUuid, "userUuid is null");

        DictionaryEntity dictionaryToDelete = verifyExistsByUuidAndUserUuid(uuid, userUuid);
        dictionaryRepository.delete(dictionaryToDelete);
    }

    @Override
    public DictionaryEntity verifyExistsByUuidAndUserUuid(String uuid, String userUuid) {
        Asserts.isNotNull(uuid, "uuid is null");
        Asserts.isNotNull(userUuid, "userUuid is null");
        return dictionaryRepository
                .findBy(uuid, userUuid)
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Dictionary with uuid = [%s] not found for user with uuid = [%s]",
                            uuid, userUuid
                    );
                    return new DictionaryNotFoundException(errorMessage);
                });
    }
}
