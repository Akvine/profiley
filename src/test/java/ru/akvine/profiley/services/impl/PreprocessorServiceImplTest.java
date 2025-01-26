package ru.akvine.profiley.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.*;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("PreprocessorServiceImpl tests")
public class PreprocessorServiceImplTest {
    @Mock
    private UserRuleService userRuleService;
    @Mock
    private UserService userService;
    @Mock
    private DictionaryService dictionaryService;
    @Mock
    private SystemRuleService systemRuleService;
    @Mock
    private FileService fileService;

    @InjectMocks
    private PreprocessorServiceImpl preprocessorService;

    @Test
    @DisplayName("Get data from services and return action")
    void get_data_from_services_and_return_action() {
        byte[] randomValues = new byte[]{0, 1, 2, 3, 4};
        String userUuid = "550e8400-e29b-41d4-a716-446655440000";
        MultipartFile mockFile = new MockMultipartFile(
                "name",
                "original_file." + FileExtension.TXT.getExtension(),
                "application/txt",
                randomValues);
        ProfileFile profileFile = new ProfileFile(userUuid, mockFile, List.of());

        Mockito.when(userRuleService.get(userUuid)).thenReturn(EMPTY_LIST);
        Mockito.when(userService.get(userUuid)).thenReturn(new User());
        Mockito.when(dictionaryService.list(userUuid)).thenReturn(EMPTY_LIST);
        Mockito.when(systemRuleService.list()).thenReturn(EMPTY_LIST);

        InputStream inputStreamResult = new ByteArrayInputStream(randomValues);
        Mockito.when(fileService.extractInputStream(mockFile))
                .thenReturn(inputStreamResult);

        ProfileAction profileAction = preprocessorService.preprocess(profileFile);

        assertThat(profileAction).isNotNull();
        assertThat(profileAction.getRules()).isEmpty();
        assertThat(profileAction.getDictionaries()).isEmpty();
        assertThat(profileAction.getFile()).isEqualTo(inputStreamResult);
    }
}
