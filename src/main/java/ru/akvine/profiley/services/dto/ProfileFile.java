package ru.akvine.profiley.services.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ProfileFile {
    private String userUuid;
    private MultipartFile file;
    @Nullable
    private List<String> excludedSystemDomainsNames;
}
