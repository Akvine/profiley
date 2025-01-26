package ru.akvine.profiley.services.domain.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.akvine.profiley.repository.entity.domain.DomainEntity;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.services.domain.base.Model;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Domain extends Model<Long> {
    private String name;
    private boolean needsMasking;
    @Nullable
    private User user;

    public Domain(DomainEntity domain) {
        super(domain);
        this.id = domain.getId();
        this.name = domain.getName();
        this.needsMasking = domain.isNeedsMasking();
        if (domain.getUser() != null) {
            this.user = new User(domain.getUser());
        }
    }

    public boolean isSystem() {
        return user == null;
    }
}
