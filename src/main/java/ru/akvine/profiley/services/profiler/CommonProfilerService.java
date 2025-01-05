package ru.akvine.profiley.services.profiler;

import com.google.common.base.Preconditions;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import ru.akvine.profiley.async.RulesAsyncProvider;
import ru.akvine.profiley.async.WordsAsyncProvider;
import ru.akvine.profiley.services.DetectByRulesService;
import ru.akvine.profiley.services.DetectByWordsService;
import ru.akvine.profiley.services.ProfilerService;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.PossibleDomain;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public abstract class CommonProfilerService implements ProfilerService {
    protected final DetectByWordsService detectByWordsService;
    protected final DetectByRulesService detectByRulesService;
//    protected final WordsAsyncProvider wordsAsyncProvider;
//    protected final RulesAsyncProvider rulesAsyncProvider;

    @Override
    public List<? extends PossibleDomain> profile(ProfileAction profileAction) {
        Preconditions.checkNotNull(profileAction, "profileAction is not null");
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
