package ru.akvine.profiley.rest.dto.dictionaries;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ListDictionaryResponse extends SuccessfulResponse {
    private List<DictionaryDto> dictionaries;
}
