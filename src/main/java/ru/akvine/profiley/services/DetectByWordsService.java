package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Dictionary;

import java.util.Collection;

public interface DetectByWordsService {
    String detect(String value, Collection<Dictionary> dictionaries);
}
