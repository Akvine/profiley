package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.services.dto.ProfileFile;

@Component
@RequiredArgsConstructor
public class ProfilerConverter {
    private final SecurityManager securityManager;

    public ProfileFile convertToProfileFile(MultipartFile file) {
        return new ProfileFile(securityManager.getCurrentUser().getId(), file);
    }
}
