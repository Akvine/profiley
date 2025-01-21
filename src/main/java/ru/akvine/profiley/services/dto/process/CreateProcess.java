package ru.akvine.profiley.services.dto.process;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.FileExtension;

@Data
@Accessors(chain = true)
public class CreateProcess {
    private String userUuid;
    private String fileName;
    private FileExtension fileExtension;
}
