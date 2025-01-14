package ru.akvine.profiley.services.dto.dictionary;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UpdateDictionary {
    private String userUuid;
    private String uuid;

    @Nullable
    private List<String> words;

    @Nullable
    private String domainName;

    @Nullable
    private String locale;

}
