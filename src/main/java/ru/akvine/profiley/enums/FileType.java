package ru.akvine.profiley.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.akvine.profiley.exceptions.file.FileTypeNotSupportedException;
import ru.akvine.profiley.utils.Asserts;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum FileType {
    TEXT(Set.of(
            FileExtension.TXT,
            FileExtension.LOG,
            FileExtension.JSON,
            FileExtension.XML,
            FileExtension.HTML,
            FileExtension.CSS,
            FileExtension.JS
    ));
    private final Collection<FileExtension> supportedFileExtensions;

    public static FileType from(FileExtension fileExtension) {
        Asserts.isNotNull(fileExtension, "fileExtension is null");

        for (FileType fileType : values()) {
            if (fileType.getSupportedFileExtensions().contains(fileExtension)) {
                return fileType;
            }
        }

        String errorMessage = String.format(
                "File with extension = [%s] has not supported type by app!",
                fileExtension
        );
        throw new FileTypeNotSupportedException(errorMessage);
    }
}
