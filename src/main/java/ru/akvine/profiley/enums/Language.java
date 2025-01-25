package ru.akvine.profiley.enums;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {
    RU("ru"),
    EN("en");

    private final String value;

    public static Language from(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Value for " + Language.class.getSimpleName() + " can't be blank!");
        }

        return switch (value.toLowerCase()) {
            case "ru" -> RU;
            case "en" -> EN;
            default -> throw new UnsupportedOperationException("Language = [" + value + "] is not supported by app!");
        };
    }
}
