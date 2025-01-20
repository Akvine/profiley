package ru.akvine.profiley.rest.validator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.exceptions.file.FileExtensionNotSupportedException;
import ru.akvine.profiley.validators.FileTypeValidator;

@Component
@RequiredArgsConstructor
public class ProfilerValidator {
    private final FileTypeValidator fileTypeValidator;

    public void validate(MultipartFile file, FileType expectedFileType) {
        fileTypeValidator.validate(file, expectedFileType);

        String nameWithoutExtension = FilenameUtils.getBaseName(file.getOriginalFilename());
        String rowExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!FileExtension.getSupportedExtensions().contains(rowExtension)) {
            String message = String.format(
                    "File with name = [%s] has not supported extension = [%s]",
                    nameWithoutExtension, rowExtension);
            throw new FileExtensionNotSupportedException(message);
        }
    }
}
