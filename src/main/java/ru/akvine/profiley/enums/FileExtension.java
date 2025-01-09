package ru.akvine.profiley.enums;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.akvine.profiley.exceptions.FileExtensionNotSupportedException;
import ru.akvine.profiley.utils.Asserts;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum FileExtension {
    TXT("txt"),
    LOG("log"),
    DOCX("docx"),
    DOC("doc"),

    CSV("csv"),
    XLSX("xlsx"),
    XLS("xls"),
    ODS("ods"),

    PDF("pdf"),
    XML("xml"),
    JSON("json");

    private final String extension;

    public static List<String> getSupportedExtensions() {
        List<String> result = new ArrayList<>();
        for (FileExtension extension : values()) {
            result.add(extension.getExtension());
        }

        return result;
    }

    public static FileExtension from(String extension) {
        Asserts.isNotNull(extension, "fileExtension is null");

        for (FileExtension enumExtension : values()) {
            if (enumExtension.getExtension().equals(extension)) {
                return enumExtension;
            }
        }

        String errorMessage = String.format(
                "File with extension = [%s] is not supported by app!",
                extension
        );
        throw new FileExtensionNotSupportedException(errorMessage);
    }
}
