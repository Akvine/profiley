package ru.akvine.profiley.services.impl;

import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.exceptions.domain.DomainNotDetectedException;
import ru.akvine.profiley.services.DetectByDictionariesService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class DetectByDictionariesServiceImpl implements DetectByDictionariesService {

    @Override
    public String detect(String value, Collection<Dictionary> dictionaries) {
        for (Dictionary dictionary : dictionaries) {
            Set<String> words = new HashSet<>(dictionary.getWords());

            if (words.contains(value)) {
                return dictionary.getDomain().getName();
            }
        }

        throw new DomainNotDetectedException("Domain for value = [" + value + "] not detected!");
    }
}
