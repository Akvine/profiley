package ru.akvine.profiley.services.profiler;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.exceptions.DomainNotDetectedException;
import ru.akvine.profiley.exceptions.ProfilingProcessException;
import ru.akvine.profiley.services.DetectByDictionariesService;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.RowProcessorService;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.dto.PossibleDomain;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.TxtPossibleDomain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
                    String possibleDomainByWords = "";
                    try {
                        possibleDomainByWords = detectByDictionariesService.detect(
                                word.toLowerCase(Locale.ROOT),
                                profileAction.getDictionaries()
                        );
                    } catch (DomainNotDetectedException exception) {
                        List<String> uuids = profileAction.getDictionaries().stream().map(Dictionary::getUuid).toList();
                        logger.debug("Not detected by dictionaries with uuids = {}", uuids);
                    }

                    if (StringUtils.isNotBlank(possibleDomainByWords)) {
                        detectedDomains.add(new TxtPossibleDomain(possibleDomainByWords, currentLineNumber));
                    }
                }

                Map<String, Boolean> possibleDomainsByRules = detectByRulesService.detect(line, profileAction.getRules());
                detectedDomains.addAll(convertToTxtPossibleDomains(possibleDomainsByRules, currentLineNumber));

                currentLineNumber += 1;
            }
        } catch (IOException exception) {
            String errorMessage = String.format(
                    "Error while profiling user file. User email: [%s]." +
                            " Name: [%s]. Extension: [%s]." +
                            " Message = [%s]",
                    profileAction.getUserEmail(),
                    profileAction.getFileName(),
                    profileAction.getExtension(),
                    exception.getMessage()
            );
            throw new ProfilingProcessException(errorMessage);
        }

        return detectedDomains;
    }

    @Override
    public FileExtension getType() {
        return FileExtension.TXT;
    }

    // TODO : вынести в отдельный класс
    private List<TxtPossibleDomain> convertToTxtPossibleDomains(Map<String, Boolean> possibleDomainsByRules,
                                                                int currentLineNumber) {
        return possibleDomainsByRules.entrySet().stream()
                .map(pair -> new TxtPossibleDomain(pair.getKey(), currentLineNumber, pair.getValue()))
                .toList();
    }
}
