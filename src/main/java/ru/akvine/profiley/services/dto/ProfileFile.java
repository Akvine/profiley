package ru.akvine.profiley.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ProfileFile {
    private String userUuid;
    private MultipartFile file;
}
