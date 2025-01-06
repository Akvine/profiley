package ru.akvine.profiley.services.dto.rule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeleteRule {
    private String uuid;
    private String userUuid;
}
