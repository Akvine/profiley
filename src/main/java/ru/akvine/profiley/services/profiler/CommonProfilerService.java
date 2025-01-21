package ru.akvine.profiley.services.profiler;

import lombok.RequiredArgsConstructor;
import ru.akvine.profiley.services.DetectByDictionariesService;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.ProfilerService;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.utils.Asserts;

import java.util.List;

@RequiredArgsConstructor
public abstract class CommonProfilerService implements ProfilerService {
    protected final DetectByDictionariesService detectByDictionariesService;
    protected final DetectByRulesService detectByRulesService;
//    protected final WordsAsyncProvider wordsAsyncProvider;
//    protected final RulesAsyncProvider rulesAsyncProvider;

    @Override
    public List<? extends DetectedDomain> profile(ProfileAction profileAction) {
        Asserts.isNotNull(profileAction, "profileAction is not null");
        return List.of();
    }

//    @PreDestroy
//    private void destroy() {
//        for (ExecutorService executorService : Arrays.asList(
//                wordsAsyncProvider.executorService(),
//                rulesAsyncProvider.executorService())) {
//            executorService.shutdown();
//        }
//    }
}
