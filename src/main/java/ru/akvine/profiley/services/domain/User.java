package ru.akvine.profiley.services.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.services.domain.base.Model;
import ru.akvine.profiley.repository.entity.UserEntity;

@Data
@Accessors(chain = true)
public class User extends Model<Long> {
    private String email;
    private String hash;

    public User(UserEntity user) {
        super(user);
        this.id = user.getId();

        this.email = user.getEmail();
        this.hash = user.getHash();
    }
}
