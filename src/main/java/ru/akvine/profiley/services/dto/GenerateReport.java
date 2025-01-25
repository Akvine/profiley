package ru.akvine.profiley.services.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenerateReport {
    private String pid;
    private String userUuid;
    @Nullable
    private String sheetName;
}
