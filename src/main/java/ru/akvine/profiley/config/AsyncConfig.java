package ru.akvine.profiley.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akvine.profiley.async.RulesAsyncProvider;
import ru.akvine.profiley.async.WordsAsyncProvider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@Configuration
public class AsyncConfig {

//    @Bean
//    public WordsAsyncProvider profileAsyncProvider(@Value("words.profiler.pool.size.core") int poolSizeCore,
//                                                   @Value("words.profiler.pool.size.maximum") int poolSizeMaximum,
//                                                   @Value("words.profiler.keep.alive.time.milliseconds") long lifeTimeMilliseconds) {
//        ExecutorService executorService = new ThreadPoolExecutor(
//                poolSizeCore, poolSizeMaximum,
//                lifeTimeMilliseconds, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy()
//        );
//        return new WordsAsyncProvider(executorService);
//    }
//
//    @Bean
//    public RulesAsyncProvider rulesAsyncProvider(@Value("rules.profiler.pool.size.core") int poolSizeCore,
//                                                 @Value("rules.profiler.pool.size.maximum") int poolSizeMaximum,
//                                                 @Value("rules.profiler.keep.alive.time.milliseconds") long lifeTimeMilliseconds) {
//        ExecutorService executorService = new ThreadPoolExecutor(
//                poolSizeCore, poolSizeMaximum,
//                lifeTimeMilliseconds, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy()
//        );
//        return new RulesAsyncProvider(executorService);
//    }
}
