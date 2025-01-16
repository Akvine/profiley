package ru.akvine.profiley.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.akvine.profiley.exceptions.common.AssertException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asserts tests")
public class AssertsTest {
    @Test
    @DisplayName("Throw exception if object is null")
    void throw_exception_of_object_is_null() {
        String errorMessage = "object is null";
        assertThatThrownBy(() -> Asserts.isNotNull(null, errorMessage))
                .isInstanceOf(AssertException.class)
                .hasMessage(errorMessage);
    }

    @Test
    @DisplayName("Not throw exception if object is not null")
    void not_throw_exception_if_object_is_not_null() {
        String errorMessage = "object is null";
        assertDoesNotThrow(() -> Asserts.isNotNull(new Object(), errorMessage));
    }
}
