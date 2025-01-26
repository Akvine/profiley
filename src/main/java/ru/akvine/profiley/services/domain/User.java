package ru.akvine.profiley.services.domain;

import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.akvine.profiley.enums.Language;
import ru.akvine.profiley.services.domain.base.Model;
import ru.akvine.profiley.repository.entity.UserEntity;

import java.util.Arrays;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class User extends Model<Long> {
    private String email;
    private String hash;
    private boolean disabledSystemDomains;
    private boolean disabledSystemRules;
    private Language language;
    private Collection<String> disabledSystemDomainsNames;

    public User(UserEntity user) {
        super(user);
        this.id = user.getId();

        this.email = user.getEmail();
        this.hash = user.getHash();
        this.disabledSystemDomains = user.isDisabledSystemDomains();
        this.disabledSystemRules = user.isDisabledSystemRules();

        if (StringUtils.isNotBlank(user.getDisabledSystemDomainsNames())) {
            this.disabledSystemDomainsNames = Arrays.stream(
                            user.getDisabledSystemDomainsNames().split(","))
                    .toList();
        }

        this.language = user.getLanguage();
    }
}
