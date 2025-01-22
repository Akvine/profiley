package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.report.GenerateReportRequest;
import ru.akvine.profiley.services.dto.GenerateReport;
import ru.akvine.profiley.utils.Asserts;

@Component
@RequiredArgsConstructor
public class ReportConverter {
    private final SecurityManager securityManager;

    @Value("${report.default.mime.type}")
    private String defaultMimeType;

    public GenerateReport convertToGenerateReport(GenerateReportRequest request) {
        Asserts.isNotNull(request, "GenerateReportRequest is null");
        return new GenerateReport()
                .setSheetName(request.getSheetName())
                .setPid(request.getPid())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public ResponseEntity<?> convertToResponseEntity(byte[] report) {
        Asserts.isNotNull(report, "report is null");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, defaultMimeType)
                .body(report);
    }
}
