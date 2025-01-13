package ru.akvine.profiley.rest.dto.dictionaries;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DictionaryDto {
    private String uuid;
    private String domainName;
    private String words;
    private String separator;
    private String locale;
}
