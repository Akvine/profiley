package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.repository.DictionaryRepository;
import ru.akvine.profiley.repository.entity.DictionaryEntity;
import ru.akvine.profiley.repository.entity.DomainEntity;
import ru.akvine.profiley.services.DictionaryService;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.dto.dictionary.CreateDictionary;
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
}
