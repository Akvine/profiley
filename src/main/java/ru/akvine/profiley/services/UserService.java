package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.repository.entity.UserEntity;

public interface UserService {
    User create(String username, String password);

    UserEntity verifyExistsByEmail(String email);

    UserEntity verifyExistsByUuid(String uuid);
}
