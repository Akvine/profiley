package ru.akvine.profiley.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("String Helper tests")
public class StringHelperTest {

    @Test
    @DisplayName("Return input if radius less than one")
    void return_input_if_radius_less_than_one() {
        String input = "Some string for masking";

        String result = StringHelper.replaceAroundMiddle(input, -1);

        assertThat(result).isNotNull();
        assertThat(input).isEqualTo(result);
    }

    @Test
    @DisplayName("Return null if radius input is null")
    void return_input_if_radius_is_null() {
        String result = StringHelper.replaceAroundMiddle((String) null, 10);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Return blank if input is blank")
    void return_blank_if_input_is_blank() {
        String result = StringHelper.replaceAroundMiddle("       ", 10);

        assertThat(result).isNotNull();
        assertThat(result).isBlank();
    }

    @Test
    @DisplayName("Return full masked result if radius more than row length")
    void return_full_masked_result_if_radius_mor_than_input_length() {
        String input = "Lorem ipsum text for testing";
        String expected = "****************************";

        String result = StringHelper.replaceAroundMiddle(input, input.length() + 100);

        assertThat(result).isNotBlank();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return masked result with default replacement = *")
    void return_masked_result_with_default_replacement() {
        String input = "Lorem ipsum text for testing!";
        String expected = "Lorem ipsum *****for testing!";

        String result = StringHelper.replaceAroundMiddle(input, 2);

        assertThat(result).isNotBlank();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return masked result with custom replacement = ?")
    void return_masked_result_with_custom_replacement() {
        String input = "Lorem ipsum text for testing!";
        String expected = "Lorem ipsum ?????for testing!";

        String result = StringHelper.replaceAroundMiddle(input, '?', 2);

        assertThat(result).isNotBlank();
        assertThat(result).isEqualTo(expected);
    }
}
