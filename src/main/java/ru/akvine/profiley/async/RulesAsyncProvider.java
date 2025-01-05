package ru.akvine.profiley.async;

import java.util.concurrent.ExecutorService;

public record RulesAsyncProvider(ExecutorService executorService) {
}
