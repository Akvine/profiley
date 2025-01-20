package ru.akvine.profiley.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

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

    public String replaceAroundMiddle(List<String> words, int visibleCount) {
        if (CollectionUtils.isEmpty(words)) {
            return "[]";
        }

        int size = words.size();

        if (size <= visibleCount * 2) {
            return words.toString();
        }

        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < visibleCount; i++) {
            result.append(words.get(i)).append(", ");
        }
        result.append("..., ");
        for (int i = size - visibleCount; i < size; i++) {
            result.append(words.get(i));
            if (i < size - 1) result.append(", ");
        }
        result.append("]");

        return result.toString();
    }
}
