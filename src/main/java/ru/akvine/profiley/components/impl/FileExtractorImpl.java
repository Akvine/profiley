package ru.akvine.profiley.components.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.components.FileExtractor;
import ru.akvine.profiley.exceptions.FileExtractException;

import java.io.InputStream;

@Component
public class FileExtractorImpl implements FileExtractor {
    @Override
    public InputStream extractInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (Exception exception) {
            String errorMessage = String.format(
                    "Some error was occurred while extract input stream from file. Message = [%s]",
                    exception.getMessage()
            );
            throw new FileExtractException(errorMessage);
        }
    }
}
