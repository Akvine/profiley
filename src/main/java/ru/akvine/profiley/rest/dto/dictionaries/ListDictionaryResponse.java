package ru.akvine.profiley.rest.dto.dictionaries;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListDictionaryResponse extends SuccessfulResponse {
    private List<DictionaryDto> dictionaries;
}
