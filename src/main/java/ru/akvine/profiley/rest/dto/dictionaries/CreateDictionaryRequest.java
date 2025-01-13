package ru.akvine.profiley.rest.dto.dictionaries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class CreateDictionaryRequest {
    @NotNull
    @NotEmpty
    private Set<String> words;

    @NotBlank
    private String separator;

    @NotBlank
    private String domainName;

    @NotBlank
    private String locale;
}
