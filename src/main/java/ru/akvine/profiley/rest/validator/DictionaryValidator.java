package ru.akvine.profiley.rest.validator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.constants.ErrorCodes;
import ru.akvine.profiley.exceptions.ValidationException;
import ru.akvine.profiley.rest.dto.dictionaries.CreateDictionaryRequest;
import ru.akvine.profiley.services.FileService;
import ru.akvine.profiley.utils.Asserts;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DictionaryValidator {
    @Value("${max.words.count.per.request}")
    private int maxWordsCount;
    @Value("${create.dictionary.by.file.extension.supports}")
    private Set<String> supportedExtensions;

    private final FileService fileService;

    public void verifyCreateDictionaryRequest(CreateDictionaryRequest request) {
        Asserts.isNotNull(request, "createDictionaryRequest is null");
        verifyWordsCount(request.getWords());
    }

    public void verifyCreateDictionaryRequest(MultipartFile file) {
        Asserts.isNotNull(file, "file is null");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!supportedExtensions.contains(extension)) {
            throw new ValidationException(
                    ErrorCodes.Validation.FILE_EXTENSION_NOT_SUPPORTED,
                    "File extension = [" + extension + "] is not supported by app!"
            );
        }

        List<String> words = fileService.parseValuesToList(fileService.extractInputStream(file));
        verifyWordsCount(words);
    }

    public void verifyWordsCount(Collection<String> words) {
        Asserts.isNotNull(words, "words is null");

        int wordsCount = words.size();
        if (wordsCount > maxWordsCount) {
            String errorMessage = String.format(
                    "Words count = [%s] is more than max available = [%s]",
                    wordsCount,
                    maxWordsCount
            );
            throw new ValidationException(
                    ErrorCodes.Validation.DICTIONARY_WORDS_COUNT_ERROR,
                    errorMessage
            );
        }
    }
}
