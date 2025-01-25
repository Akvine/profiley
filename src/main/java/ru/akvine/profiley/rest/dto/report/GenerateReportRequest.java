package ru.akvine.profiley.rest.dto.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenerateReportRequest {
    @NotBlank
    private String pid;

    private String sheetName;

    private String reportName;
}
