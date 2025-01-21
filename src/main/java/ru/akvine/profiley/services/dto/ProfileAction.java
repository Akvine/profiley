package ru.akvine.profiley.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.FileExtension;
import ru.akvine.profiley.services.domain.Dictionary;
import ru.akvine.profiley.services.domain.Rule;

import java.io.InputStream;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class ProfileAction {
    private Collection<Dictionary> dictionaries;
    private Collection<Rule> rules;
    private FileExtension extension;
    private InputStream file;
    private String fileName;
    private String userEmail;
}
