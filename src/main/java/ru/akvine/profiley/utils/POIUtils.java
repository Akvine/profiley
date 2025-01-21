package ru.akvine.profiley.utils;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Workbook;
import ru.akvine.profiley.exceptions.common.WorkbookMappingException;

import java.io.ByteArrayOutputStream;

@UtilityClass
public class POIUtils {
    public byte[] mapToBytes(Workbook workbook) {
        validate(workbook);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            throw new WorkbookMappingException(exception);
        }
    }

    private void validate(Workbook workbook) {
        if (workbook == null) {
            throw new IllegalArgumentException("Error while map workbook to bytes. Workbook can't be null!");
        }
    }
}
