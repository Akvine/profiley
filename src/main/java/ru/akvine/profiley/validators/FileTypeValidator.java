package ru.akvine.profiley.validators;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.constants.ErrorCodes;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.exceptions.common.ValidationException;
import ru.akvine.profiley.exceptions.file.FileExtensionNotSupportedException;
import ru.akvine.profiley.exceptions.file.FileTypeNotSupportedException;
import ru.akvine.profiley.utils.Asserts;

@Component
public class FileTypeValidator {
    public void validate(MultipartFile file, FileType expectedType) {
        Asserts.isNotNull(file, "file is null");
        Asserts.isNotNull(expectedType, "expectedType is null");

        String rowExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        FileExtension fileExtension;
        try {
            fileExtension = FileExtension.from(rowExtension);
        } catch (FileExtensionNotSupportedException exception) {
            throw new ValidationException(
                    ErrorCodes.Validation.FILE_EXTENSION_NOT_SUPPORTED,
                    exception.getMessage()
            );
        }

        FileType fileType = FileType.from(fileExtension);

        if (fileType != expectedType) {
            String nameWithoutExtension = FilenameUtils.getBaseName(file.getOriginalFilename());
            String errorMessage = String.format(
                    "File with name = [%s] has invalid type = [%s]",
                    nameWithoutExtension, fileType
            );

            throw new ValidationException(
                    ErrorCodes.Validation.FILE_TYPE_NOT_VALID_ERROR,
                    errorMessage
            );
        }
    }
}
