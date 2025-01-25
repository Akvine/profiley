package ru.akvine.profiley.services;

import ru.akvine.profiley.enums.Language;

public interface MessageResolverService {
    String message(String code);

    String message(String code, Language language);

    String message(String code, Language language, Object... params);
}
