package ru.akvine.profiley.providers;

import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.report.ReportGenerator;

import java.util.Map;

public record ReportGeneratorsProvider(Map<FileExtension, ReportGenerator> generators) {
    public ReportGenerator getBy(FileExtension fileExtension) {
        if (!generators.containsKey(fileExtension)) {
            throw new IllegalArgumentException("Report generator by file extension = [" + fileExtension + "] is not supported by app!");
        }
        return generators.get(fileExtension);
    }
}
