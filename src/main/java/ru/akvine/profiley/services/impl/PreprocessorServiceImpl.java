package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.components.FileExtractor;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.PreprocessorService;
import ru.akvine.profiley.services.SystemRuleService;
import ru.akvine.profiley.services.UserRuleService;
import ru.akvine.profiley.services.WordService;
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
    private final WordService wordService;
    private final FileExtractor fileExtractor;

    @Override
    public ProfileAction preprocess(ProfileFile profileFile) {
        long userId = profileFile.getUserId();
        String extension = FilenameUtils.getExtension(profileFile
                .getFile()
                .getOriginalFilename());

        List<Dictionary> dictionaries = wordService.get(userId);
        List<Rule> userRules = userRuleService.get(userId);
        List<Rule> systemRules = systemRuleService.list();

        return new ProfileAction()
                .setExtension(FileExtension.from(extension))
                .setFile(fileExtractor.extractInputStream(profileFile.getFile()))
                .setRules(ListUtils.union(userRules, systemRules))
                .setDictionaries(dictionaries);
    }
}
