package ru.akvine.profiley.rest.converter;

import io.micrometer.common.util.StringUtils;
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

    private static final String HEADER_PREFIX = "attachment; filename=";

    @Value("${report.default.mime.type}")
    private String defaultMimeType;
    @Value("${report.default.extension}")
    private String defaultExtension;

    public GenerateReport convertToGenerateReport(GenerateReportRequest request) {
        Asserts.isNotNull(request, "GenerateReportRequest is null");
        return new GenerateReport()
                .setSheetName(request.getSheetName())
                .setPid(request.getPid())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public ResponseEntity<?> convertToResponseEntity(byte[] report, String reportName) {
        Asserts.isNotNull(report, "report is null");
        String formattedName;
        if (StringUtils.isBlank(reportName)) {
            formattedName = HEADER_PREFIX + "response." + defaultExtension;
        } else {
            formattedName = HEADER_PREFIX + reportName + "." + defaultExtension;
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, formattedName)
                .header(HttpHeaders.CONTENT_TYPE, defaultMimeType)
                .body(report);
    }
}
