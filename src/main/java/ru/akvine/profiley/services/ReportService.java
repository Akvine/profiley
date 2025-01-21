package ru.akvine.profiley.services;

import ru.akvine.profiley.services.dto.GenerateReport;

public interface ReportService {
    byte[] generate(GenerateReport generateReport);
}
