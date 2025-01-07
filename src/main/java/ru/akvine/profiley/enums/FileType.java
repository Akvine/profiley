package ru.akvine.profiley.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum FileType {
    TEXT(Set.of(
            FileExtension.TXT,
            FileExtension.LOG,
            FileExtension.DOCX
    )),
    COLUMN(Set.of(
            FileExtension.XLSX,
            FileExtension.CSV,
            FileExtension.ODS
    )),
    SPECIFIC(Set.of(
            FileExtension.PDF
    ));

    private final Collection<FileExtension> supportedFileExtensions;
}
