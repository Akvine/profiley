package ru.akvine.profiley.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum FileType {
    TEXT(Set.of(
            FileExtension.TXT
    ));
    private final Collection<FileExtension> supportedFileExtensions;
}
