package ru.akvine.profiley.services.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.akvine.profiley.services.domain.base.Model;
import ru.akvine.profiley.repository.entity.DictionaryEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Dictionary extends Model<Long> {
    private List<String> words;
    private String separator;
    @Nullable
    private Locale locale;
    private Domain domain;

    public Dictionary(DictionaryEntity dictionaryEntity) {
        this.id = dictionaryEntity.getId();
        this.uuid = dictionaryEntity.getUuid();

        this.separator = dictionaryEntity.getSeparator();
        this.words = Arrays.asList(dictionaryEntity
                .getWords()
                .split(separator));
        if (dictionaryEntity.getLocale() != null) {
            this.locale = Locale.of(dictionaryEntity.getLocale());
        }

        this.domain = new Domain(dictionaryEntity.getDomain());
    }
}
