package ru.akvine.profiley.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FileService {
    List<String> parseValuesToList(InputStream inputStream);

    InputStream extractInputStream(MultipartFile file);
}
