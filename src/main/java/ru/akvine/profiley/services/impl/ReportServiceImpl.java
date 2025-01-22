package ru.akvine.profiley.services.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ProcessState;
import ru.akvine.profiley.exceptions.common.ReportGenerationException;
import ru.akvine.profiley.providers.ReportGeneratorsProvider;
import ru.akvine.profiley.repository.entity.ProcessEntity;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.ReportService;
import ru.akvine.profiley.services.dto.GenerateReport;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.DateTimeUtils;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportGeneratorsProvider reportGeneratorsProvider;
    private final ProcessService processService;

    @Override
    public byte[] generate(GenerateReport generateReport) {
        Asserts.isNotNull(generateReport, "generateReport is null");

        ProcessEntity process = processService.verifyExists(generateReport.getPid(), generateReport.getUserUuid());

        ProcessState state = process.getProcessState();
        if (state != ProcessState.COMPLETED_SUCCESSFUL) {
            String errorMessage = String.format(
                    "Can't generate report for process (pid = [%s]) with state = [%s] report! Required state : [%s]",
                    process.getPid(), state, ProcessState.COMPLETED_SUCCESSFUL);
            throw new ReportGenerationException(errorMessage);
        }

        if (StringUtils.isBlank(generateReport.getSheetName())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
            generateReport.setSheetName(process.getFileName() +
                    "_" +
                    DateTimeUtils.formatLocalDateTime(DateTimeUtils.toLocalDateTime(process.getStartedDate()), formatter));
        }

        return reportGeneratorsProvider.getBy(process.getFileExtension()).generate(generateReport);
    }
}
