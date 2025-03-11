package ru.akvine.profiley.providers;

import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.services.report.ReportGenerator;

import java.util.Map;

public record ReportGeneratorsProvider(Map<FileType, ReportGenerator> generators) {
    public ReportGenerator getBy(FileType fileType) {
        if (!generators.containsKey(fileType)) {
            throw new IllegalArgumentException("Report generator for file type = [" + fileType + "] is not supported by app!");
        }
        return generators.get(fileType);
    }
}
