package ru.akvine.profiley.services;

import java.util.List;

public interface RowProcessorService {
    List<String> tokenize(String row);

    String trimSpecialSymbols(String row);
}
