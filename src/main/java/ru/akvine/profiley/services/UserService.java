package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.dto.user.UpdateUser;

public interface UserService {
    User create(String username, String password);

    User get(String uuid);

    User update(UpdateUser updateUser);

    UserEntity verifyExistsByEmail(String email);

    UserEntity verifyExistsByUuid(String uuid);
}
