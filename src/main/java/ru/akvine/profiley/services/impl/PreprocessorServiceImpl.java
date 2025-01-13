package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.*;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreprocessorServiceImpl implements PreprocessorService {
    private final UserRuleService userRuleService;
    private final SystemRuleService systemRuleService;
    private final DictionaryService dictionaryService;
    private final FileService fileService;

    @Override
    public ProfileAction preprocess(ProfileFile profileFile) {
        long userId = profileFile.getUserId();
        String extension = FilenameUtils.getExtension(profileFile
                .getFile()
                .getOriginalFilename());

        List<Dictionary> dictionaries = dictionaryService.list(userId);
        List<Rule> userRules = userRuleService.get(userId);
        List<Rule> systemRules = systemRuleService.list();

        return new ProfileAction()
                .setExtension(FileExtension.from(extension))
                .setFile(fileService.extractInputStream(profileFile.getFile()))
                .setRules(ListUtils.union(userRules, systemRules))
                .setDictionaries(dictionaries);
    }
}
