package ru.akvine.profiley.rest.dto.rule;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.rest.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListRuleResponse extends SuccessfulResponse {
    private List<RuleDto> rules;
}
