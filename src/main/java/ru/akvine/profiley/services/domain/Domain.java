package ru.akvine.profiley.services.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.services.domain.base.Model;
import ru.akvine.profiley.repository.entity.DomainEntity;

@Data
@Accessors(chain = true)
public class Domain extends Model<Long> {
    private String name;
    @Nullable
    private User user;

    public Domain(DomainEntity domain) {
        super(domain);
        this.id = domain.getId();
        this.name = domain.getName();
        this.user = new User(domain.getUser());
    }

    public boolean isSystem() {
        return user == null;
    }
}
