package ru.akvine.profiley.utils;

import lombok.experimental.UtilityClass;
import ru.akvine.profiley.exceptions.AssertException;

@UtilityClass
public class Asserts {
    public void isNotNull(Object object, String errorMessage) {
        if (object == null) {
            throw new AssertException(errorMessage);
        }
    }
}
