package ru.akvine.profiley.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.exceptions.FileExtractException;
import ru.akvine.profiley.exceptions.FileParseException;
import ru.akvine.profiley.services.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<String> parseValuesToList(InputStream inputStream) {
        List<String> parsedValues = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parsedValues.add(line);
            }
        } catch (IOException exception) {
            String errorMessage = String.format(
                    "Some error was occurred while parse file = [%s]",
                    exception.getMessage()
            );
            throw new FileParseException(errorMessage);
        }

        return parsedValues;
    }

    @Override
    public InputStream extractInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException exception) {
            String errorMessage = String.format(
                    "Some error was occurred while extract input stream from file. Message = [%s]",
                    exception.getMessage()
            );
            throw new FileExtractException(errorMessage);
        }
    }
}
