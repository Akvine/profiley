package ru.akvine.profiley.services;

import org.springframework.transaction.annotation.Transactional;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.dto.dictionary.CreateDictionary;

import java.util.List;

public interface DictionaryService {
    @Transactional
    List<Dictionary> list(long userId);

    @Transactional
    Dictionary create(CreateDictionary createDictionary);
}
