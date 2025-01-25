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
import ru.akvine.profiley.enums.Language;
import ru.akvine.profiley.exceptions.common.ReportGenerationException;
import ru.akvine.profiley.providers.DetectedDomainsServicesProvider;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.MessageResolverService;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.domain.DetectedTextDomain;
import ru.akvine.profiley.services.domain.domain.Domain;
import ru.akvine.profiley.services.dto.GenerateReport;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.POIUtils;
import ru.akvine.profiley.utils.StringHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.akvine.profiley.constants.MessageResolverCodes.Report.*;

@Service
@RequiredArgsConstructor
public class TxtReportGenerator implements ReportGenerator {
    private final DetectedDomainsServicesProvider detectedDomainsServicesProvider;
    private final DomainService domainService;
    private final MessageResolverService messageResolverService;
    private final UserService userService;

    @Override
    public byte[] generate(GenerateReport generateReport) {
        Asserts.isNotNull(generateReport, "generateReport is null");

        String userUuid = generateReport.getUserUuid();
        String pid = generateReport.getPid();

        Language userLanguage = userService.get(userUuid).getLanguage();
        List<DetectedTextDomain> detectedTextDomainList =
                (List<DetectedTextDomain>) detectedDomainsServicesProvider
                        .getByType(FileType.from(getExtension()))
                        .list(pid, userUuid);
        List<String> domainNames = detectedTextDomainList.stream()
                .map(DetectedTextDomain::getDomainName)
                .toList();
        ListDomains listDomains = new ListDomains()
                .setDomainNames(domainNames)
                .setUserUuid(userUuid);
        Map<String, Domain> domains = domainService.get(listDomains).stream()
                .collect(Collectors.toMap(Domain::getName, domain -> domain));

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(generateReport.getSheetName());
            createHeaders(sheet, userLanguage);

            for (int i = 0; i < detectedTextDomainList.size(); ++i) {
                Row row = sheet.createRow(i + 1);

                Cell domainName = row.createCell(0);
                domainName.setCellValue(detectedTextDomainList.get(i).getDomainName());

                Cell valueName = row.createCell(1);
                String value = detectedTextDomainList.get(i).getValue();
                // TODO: вынести логику обработки значений до генерации отчета. Это не его обязанность
                Domain domain = domains.get(detectedTextDomainList.get(i).getDomainName());
                if (domain != null && domain.isNeedsMasking()) {
                    int radius = value.length() / 4;
                    valueName.setCellValue(StringHelper.replaceAroundMiddle(value, radius));
                } else {
                    valueName.setCellValue(value);
                }

                Cell correctName = row.createCell(2);
                correctName.setCellValue(detectedTextDomainList.get(i).isCorrect() ?
                        messageResolverService.message(BOOLEAN_TRUE_CODE, userLanguage) :
                        messageResolverService.message(BOOLEAN_FALSE_CODE, userLanguage));

                Cell lineNumberName = row.createCell(3);
                lineNumberName.setCellValue(detectedTextDomainList.get(i).getLineNumber());
            }

            return POIUtils.mapToBytes(workbook);
        } catch (IOException e) {
            String errorMessage = String.format("Error while generate report: [%s]", e.getMessage());
            throw new ReportGenerationException(errorMessage);
        }
    }

    private void createHeaders(Sheet sheet, Language language) {
        Row headersRow = sheet.createRow(0);

        Cell domainName = headersRow.createCell(0);
        domainName.setCellValue(messageResolverService.message(DOMAIN_NAME_CODE, language));

        Cell valueName = headersRow.createCell(1);
        valueName.setCellValue(messageResolverService.message(VALUE_NAME_CODE, language));

        Cell correctName = headersRow.createCell(2);
        correctName.setCellValue(messageResolverService.message(CORRECT_NAME_CODE, language));

        Cell lineNumberName = headersRow.createCell(3);
        lineNumberName.setCellValue(messageResolverService.message(LINE_NUMBER_NAME_CODE, language));
    }

    @Override
    public FileExtension getExtension() {
        return FileExtension.TXT;
    }
}
