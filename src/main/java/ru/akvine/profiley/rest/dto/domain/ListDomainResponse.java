package ru.akvine.profiley.rest.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListDomainResponse extends SuccessfulResponse {
    private List<DomainDto> domains;
}
