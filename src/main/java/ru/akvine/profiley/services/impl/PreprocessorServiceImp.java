package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.components.FileExtractor;
import ru.akvine.profiley.services.domain.Rule;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.enums.file.FileExtension;
import ru.akvine.profiley.services.PreprocessorService;
import ru.akvine.profiley.services.RuleService;
import ru.akvine.profiley.services.WordService;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreprocessorServiceImp implements PreprocessorService {
    private final RuleService ruleService;
    private final WordService wordService;
    private final FileExtractor fileExtractor;

    @Override
    public ProfileAction preprocess(ProfileFile profileFile) {
        long userId = profileFile.getUserId();
        String extension = FilenameUtils.getExtension(profileFile
                .getFile()
                .getOriginalFilename());

        List<Dictionary> dictionaries = wordService.get(userId);
        List<Rule> rules = ruleService.get(userId);

        return new ProfileAction()
                .setExtension(FileExtension.from(extension))
                .setFile(fileExtractor.extractInputStream(profileFile.getFile()))
                .setRules(rules)
                .setDictionaries(dictionaries);
    }
}
