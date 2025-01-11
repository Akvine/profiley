package ru.akvine.profiley.services.profiler;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.exceptions.DomainNotDetectedException;
import ru.akvine.profiley.exceptions.ProfileActionException;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.DetectByDictionariesService;
import ru.akvine.profiley.services.RowProcessorService;
import ru.akvine.profiley.services.dto.PossibleDomain;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.TxtPossibleDomain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TxtProfilerService extends CommonProfilerService {
    private final RowProcessorService rowProcessorService;

    public TxtProfilerService(DetectByDictionariesService detectByDictionariesService,
                              DetectByRulesService detectByRulesService,
                              RowProcessorService rowProcessorService) {
        super(detectByDictionariesService, detectByRulesService);
        this.rowProcessorService = rowProcessorService;

    }

    @Override
    public List<? extends PossibleDomain> profile(ProfileAction profileAction) {
        super.profile(profileAction);
        InputStream file = profileAction.getFile();
        List<TxtPossibleDomain> detectedDomains = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String line;
            int currentLineNumber = 1;

            while ((line = reader.readLine()) != null) {
                List<String> lineWords = rowProcessorService.tokenize(line);
                for (String word : lineWords) {
                    String transformedWord = rowProcessorService.trimSpecialSymbols(word).toLowerCase();

                    String possibleDomainByWords = "";
                    try {
                        possibleDomainByWords = detectByDictionariesService.detect(transformedWord, profileAction.getDictionaries());
                    } catch (DomainNotDetectedException exception) {
                        logger.info("Not detected by words");
                    }

                    if (StringUtils.isNotBlank(possibleDomainByWords)) {
                        detectedDomains.add(new TxtPossibleDomain(possibleDomainByWords, currentLineNumber));
                    }

                    List<String> possibleDomainsByRules = detectByRulesService.detect(transformedWord, profileAction.getRules());
                    detectedDomains.addAll(convertToTxtPossibleDomains(possibleDomainsByRules, currentLineNumber));
                }

                currentLineNumber += 1;
            }
        } catch (Exception exception) {
            String errorMessage = String.format(
                    "Error while profiling action. Message = [%s]",
                    exception.getMessage()
            );
            throw new ProfileActionException(errorMessage);
        }

        return detectedDomains;
    }

    @Override
    public FileExtension getType() {
        return FileExtension.TXT;
    }

    // TODO : вынести в отдельный класс
    private List<TxtPossibleDomain> convertToTxtPossibleDomains(List<String> possibleDomainsByRules,
                                                                int currentLineNumber) {
        return possibleDomainsByRules
                .stream()
                .map(domainName -> new TxtPossibleDomain(domainName, currentLineNumber))
                .toList();
    }
}
