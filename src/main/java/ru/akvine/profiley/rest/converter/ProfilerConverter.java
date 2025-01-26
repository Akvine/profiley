package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.services.dto.ProfileFile;
import ru.akvine.profiley.utils.Asserts;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfilerConverter {
    private final SecurityManager securityManager;

    public ProfileFile convertToProfileFile(MultipartFile file, List<String> excludedSystemDomainsNames) {
        Asserts.isNotNull(file, "file is null");

        return new ProfileFile(
                securityManager.getCurrentUser().getUuid(),
                file,
                excludedSystemDomainsNames
        );
    }
}
