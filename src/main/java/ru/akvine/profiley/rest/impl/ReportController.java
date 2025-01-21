package ru.akvine.profiley.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.rest.ReportControllerMeta;
import ru.akvine.profiley.rest.converter.ReportConverter;
import ru.akvine.profiley.rest.dto.report.GenerateReportRequest;
import ru.akvine.profiley.services.ReportService;
import ru.akvine.profiley.services.dto.GenerateReport;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportControllerMeta {
    private final ReportService reportService;
    private final ReportConverter reportConverter;

    @Override
    public ResponseEntity<byte[]> generate(GenerateReportRequest request) {
        GenerateReport generateReport = reportConverter.convertToGenerateReport(request);
        return ResponseEntity.ok().body(reportService.generate(generateReport));
    }
}
