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
import ru.akvine.profiley.components.FileExtractor;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.UserRuleService;
import ru.akvine.profiley.services.WordService;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("PreprocessorServiceImpl tests")
public class PreprocessorServiceImplTest {
    @Mock
    private UserRuleService userRuleService;
    @Mock
    private WordService wordService;
    @Mock
    private FileExtractor fileExtractor;

    @InjectMocks
    private PreprocessorServiceImpl preprocessorService;

    @Test
    @DisplayName("Get data from services and return action")
    void get_data_from_services_and_return_action() {
        byte[] randomValues = new byte[]{0, 1, 2, 3, 4};
        long userId = 1L;
        MultipartFile mockFile = new MockMultipartFile(
                "name",
                "original_file." + FileExtension.TXT.getExtension(),
                "application/txt",
                randomValues);
        ProfileFile profileFile = new ProfileFile(1L, mockFile);

        Mockito.when(userRuleService.get(userId)).thenReturn(EMPTY_LIST);
        Mockito.when(wordService.get(userId)).thenReturn(EMPTY_LIST);

        InputStream inputStreamResult = new ByteArrayInputStream(randomValues);
        Mockito.when(fileExtractor.extractInputStream(mockFile))
                .thenReturn(inputStreamResult);

        ProfileAction profileAction = preprocessorService.preprocess(profileFile);

        assertThat(profileAction).isNotNull();
        assertThat(profileAction.getRules()).isEmpty();
        assertThat(profileAction.getDictionaries()).isEmpty();
        assertThat(profileAction.getFile()).isEqualTo(inputStreamResult);
    }
}
