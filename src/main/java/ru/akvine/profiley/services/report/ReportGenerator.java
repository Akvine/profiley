package ru.akvine.profiley.services.report;

import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.dto.GenerateReport;

public interface ReportGenerator {
    byte[] generate(GenerateReport generateReport);

    FileExtension getExtension();
}
