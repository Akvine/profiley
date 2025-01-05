package ru.akvine.profiley.components;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileExtractor {
    InputStream extractInputStream(MultipartFile file);
}
