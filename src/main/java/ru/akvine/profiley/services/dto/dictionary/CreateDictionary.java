package ru.akvine.profiley.services.dto.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreateDictionary {
    private List<String> words;
    private String separator;
    private String domainName;
    private String locale;
    private String userUuid;
}
