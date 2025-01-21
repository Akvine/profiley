package ru.akvine.profiley.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenerateReport {
    private String pid;
    private String userUuid;
    private String sheetName;
}
