package ru.akvine.profiley.rest.validator;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.enums.file.FileExtension;
import ru.akvine.profiley.exceptions.FileExtensionNotSupportedException;

@Component
public class ProfilerValidator {

    public void validate(MultipartFile file) {
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
