package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.Dictionary;

import java.util.List;

public interface WordService {
    List<Dictionary> get(long userId);
}
