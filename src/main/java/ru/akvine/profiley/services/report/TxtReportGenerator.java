package ru.akvine.profiley.services.report;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.exceptions.common.ReportGenerationException;
import ru.akvine.profiley.providers.DetectedDomainsServicesProvider;
import ru.akvine.profiley.services.domain.domain.DetectedTextDomain;
import ru.akvine.profiley.services.dto.GenerateReport;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.POIUtils;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TxtReportGenerator implements ReportGenerator {
    private final DetectedDomainsServicesProvider detectedDomainsServicesProvider;

    @Override
    public byte[] generate(GenerateReport generateReport) {
        Asserts.isNotNull(generateReport, "generateReport is null");

        String userUuid = generateReport.getUserUuid();
        String pid = generateReport.getPid();

        List<DetectedTextDomain> detectedTextDomainList = (List<DetectedTextDomain>) detectedDomainsServicesProvider
                .getByType(FileType.from(getExtension()))
                .list(pid, userUuid);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(generateReport.getSheetName());
            createHeaders(sheet);

            for (int i = 0; i < detectedTextDomainList.size(); ++i) {
                Row row = sheet.createRow(i + 1);

                Cell domainName = row.createCell(0);
                domainName.setCellValue(detectedTextDomainList.get(i).getDomainName());

                Cell valueName = row.createCell(1);
                valueName.setCellValue(detectedTextDomainList.get(i).getValue());

                Cell correctName = row.createCell(2);
                correctName.setCellValue(detectedTextDomainList.get(i).isCorrect() ? "Да" : "Нет");

                Cell lineNumberName = row.createCell(3);
                lineNumberName.setCellValue(detectedTextDomainList.get(i).getLineNumber());
            }

            return POIUtils.mapToBytes(workbook);
        } catch (IOException e) {
            String errorMessage = String.format("Error while generate report: [%s]", e.getMessage());
            throw new ReportGenerationException(errorMessage);
        }
    }

    private void createHeaders(Sheet sheet) {
        Row headersRow = sheet.createRow(0);

        // TODO: Добавить локализацию колонок в зависимости от языка в настройках пользователя
        Cell domainName = headersRow.createCell(0);
        domainName.setCellValue("Домен");

        Cell valueName = headersRow.createCell(1);
        valueName.setCellValue("Значение");

        Cell correctName = headersRow.createCell(2);
        correctName.setCellValue("Корректный");

        Cell lineNumberName = headersRow.createCell(3);
        lineNumberName.setCellValue("Номер строки");
    }

    @Override
    public FileExtension getExtension() {
        return FileExtension.TXT;
    }
}
