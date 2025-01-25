package ru.akvine.profiley.services.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.Language;
import ru.akvine.profiley.services.domain.base.Model;
import ru.akvine.profiley.repository.entity.UserEntity;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class User extends Model<Long> {
    private String email;
    private String hash;
    private boolean disabledSystemDomains;
    private boolean disabledSystemRules;
    private Language language;

    public User(UserEntity user) {
        super(user);
        this.id = user.getId();

        this.email = user.getEmail();
        this.hash = user.getHash();
        this.disabledSystemDomains = user.isDisabledSystemDomains();
        this.disabledSystemRules = user.isDisabledSystemRules();
        this.language = user.getLanguage();
    }
}
