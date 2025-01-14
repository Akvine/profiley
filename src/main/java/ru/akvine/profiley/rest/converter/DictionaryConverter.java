package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.dictionaries.CreateDictionaryRequest;
import ru.akvine.profiley.rest.dto.dictionaries.DictionaryDto;
import ru.akvine.profiley.rest.dto.dictionaries.ListDictionaryResponse;
import ru.akvine.profiley.rest.dto.dictionaries.UpdateDictionaryRequest;
import ru.akvine.profiley.services.FileService;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.dto.dictionary.CreateDictionary;
import ru.akvine.profiley.services.dto.dictionary.UpdateDictionary;
import ru.akvine.profiley.utils.Asserts;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DictionaryConverter {
    private final SecurityManager securityManager;
    private final FileService fileService;

    public CreateDictionary convertToCreateDictionary(CreateDictionaryRequest request) {
        Asserts.isNotNull(request, "createDictionaryRequest is null");
        return new CreateDictionary()
                .setDomainName(request.getDomainName())
                .setWords(request.getWords().stream().map(String::toLowerCase).toList())
                .setUserUuid(securityManager.getCurrentUser().getUuid())
                .setSeparator(request.getSeparator())
                .setLocale(request.getLocale());
    }

    public CreateDictionary convertToCreateDictionary(MultipartFile file,
                                                      String separator,
                                                      String locale,
                                                      String domainName) {
        Asserts.isNotNull(file, "file is null");
        Asserts.isNotNull(separator, "separator is null");
        Asserts.isNotNull(locale, "locale is null");
        Asserts.isNotNull(domainName, "domainName is null");
        return new CreateDictionary()
                .setUserUuid(securityManager.getCurrentUser().getUuid())
                .setLocale(locale)
                .setSeparator(separator)
                .setDomainName(domainName)
                .setWords(fileService.parseValuesToList(fileService.extractInputStream(file)));
    }

    public UpdateDictionary convertToUpdateDictionary(UpdateDictionaryRequest request) {
        Asserts.isNotNull(request, "updateDictionaryRequest is null");
        return new UpdateDictionary()
                .setDomainName(request.getDomainName())
                .setUuid(request.getUuid())
                .setWords(request.getWords() == null ? null : request.getWords().stream().toList())
                .setLocale(request.getLocale())
                .setDomainName(request.getDomainName())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public ListDictionaryResponse convertToListDictionaryResponse(List<Dictionary> dictionaries) {
        return new ListDictionaryResponse()
                .setDictionaries(dictionaries.stream().map(this::buildToDictionaryDto).toList());
    }

    private DictionaryDto buildToDictionaryDto(Dictionary dictionary) {
        return new DictionaryDto()
                .setUuid(dictionary.getUuid())
                .setDomainName(dictionary.getDomain().getName())
                .setSeparator(dictionary.getSeparator())
                .setWords("[]" + (dictionary.getWords().isEmpty() ? "" : dictionary.getWords().getFirst()));
    }
}
