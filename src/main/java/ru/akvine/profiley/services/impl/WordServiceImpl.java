package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.repository.DictionaryRepository;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.WordService;
import ru.akvine.profiley.services.dto.domain.ListDomains;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
    private final DictionaryRepository dictionaryRepository;

    private final DomainService domainService;

    @Override
    public List<Dictionary> get(long userId) {
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
}
