package ru.akvine.profiley.rest.dto.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ListDomainResponse extends SuccessfulResponse {
    private List<DomainDto> domains;
}
