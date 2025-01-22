package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.profiley.rest.dto.report.GenerateReportRequest;

@RequestMapping(value = "/reports")
public interface ReportControllerMeta {
    @GetMapping
    ResponseEntity<?> generate(@RequestBody @Valid GenerateReportRequest request);
}
