package ru.akvine.profiley.rest.dto.dictionaries;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class UpdateDictionaryRequest {
    @NotBlank
    private String uuid;

    private String domainName;

    private Set<String> words;

    private String locale;
}
