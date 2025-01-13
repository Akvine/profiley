package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.RowProcessorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RowProcessorServiceImpl implements RowProcessorService {
    private final static String REGEXP = "^[^\\p{L}\\p{N}]+|[^\\p{L}\\p{N}]+$";

    @Override
    public List<String> tokenize(String row) {
        return List.of(row.split(" "));
    }

    @Override
    public String trimSpecialSymbols(String row) {
        return row.replaceAll(REGEXP, "");
    }
}
