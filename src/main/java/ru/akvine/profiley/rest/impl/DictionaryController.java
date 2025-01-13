package ru.akvine.profiley.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.DictionaryControllerMeta;
import ru.akvine.profiley.rest.converter.DictionaryConverter;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.dictionaries.CreateDictionaryRequest;
import ru.akvine.profiley.rest.validator.DictionaryValidator;
import ru.akvine.profiley.services.DictionaryService;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.dto.dictionary.CreateDictionary;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DictionaryController implements DictionaryControllerMeta {
    private final DictionaryService dictionaryService;
    private final DictionaryValidator dictionaryValidator;
    private final DictionaryConverter dictionaryConverter;
    private final SecurityManager securityManager;

    @Override
    public Response list() {
        List<Dictionary> dictionaries = dictionaryService.list(securityManager.getCurrentUser().getId());
        return dictionaryConverter.convertToListDictionaryResponse(dictionaries);
    }

    @Override
    public Response createByRequest(@RequestBody @Valid CreateDictionaryRequest request) {
        dictionaryValidator.verifyCreateDictionaryRequest(request);
        CreateDictionary createDictionary = dictionaryConverter.convertToCreateDictionary(request);
        Dictionary createdDictionary = dictionaryService.create(createDictionary);
        return dictionaryConverter.convertToListDictionaryResponse(List.of(createdDictionary));
    }

    @Override
    public Response createByFile(MultipartFile file,
                                 String separator,
                                 String locale,
                                 String domainName) {
        dictionaryValidator.verifyCreateDictionaryRequest(file);
        CreateDictionary createDictionary = dictionaryConverter.convertToCreateDictionary(file, separator, locale, domainName);
        Dictionary createdDictionary = dictionaryService.create(createDictionary);
        return dictionaryConverter.convertToListDictionaryResponse(List.of(createdDictionary));
    }
}
