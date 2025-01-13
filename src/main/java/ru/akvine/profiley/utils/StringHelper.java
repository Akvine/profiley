package ru.akvine.profiley.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

@UtilityClass
public class StringHelper {
    private static final char DEFAULT_REPLACEMENT = '*';

    @Nullable
    public String replaceAroundMiddle(String input, int radius) {
        return replaceAroundMiddle(input, DEFAULT_REPLACEMENT, radius);
    }

    @Nullable
    public String replaceAroundMiddle(String input, char replacement, int radius) {
        if (StringUtils.isBlank(input) || radius < 1) {
            return input;
        }

        int length = input.length();
        int middle = length / 2;

        int start = Math.max(0, middle - radius);
        int end = Math.min(length, middle + radius + (length % 2 == 0 ? 0 : 1));

        StringBuilder result = new StringBuilder(input);
        for (int i = start; i < end; i++) {
            result.setCharAt(i, replacement);
        }

        return result.toString();
    }
}
