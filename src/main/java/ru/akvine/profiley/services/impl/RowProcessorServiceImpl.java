package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.RowProcessorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RowProcessorServiceImpl implements RowProcessorService {
    private final static String SPECIAL_CONSTANTS_REGEXP = "[!\"#$%&'()*+,\\-./:;<=>?@\\\\[\\\\]^_`{|}~\\\\\\\\]";

    @Override
    public List<String> tokenize(String row) {
        String formattedRow = row.replaceAll(SPECIAL_CONSTANTS_REGEXP, "");
        return List.of(formattedRow.split(" "));
    }
}
