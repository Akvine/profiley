package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.report.GenerateReportRequest;
import ru.akvine.profiley.services.dto.GenerateReport;
import ru.akvine.profiley.utils.Asserts;

@Component
@RequiredArgsConstructor
public class ReportConverter {
    private final SecurityManager securityManager;

    public GenerateReport convertToGenerateReport(GenerateReportRequest request) {
        Asserts.isNotNull(request, "GenerateReportRequest is null");
        return new GenerateReport()
                .setSheetName(request.getSheetName())
                .setPid(request.getPid())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }
}
