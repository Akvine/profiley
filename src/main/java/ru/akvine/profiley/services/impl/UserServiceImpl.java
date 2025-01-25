package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.exceptions.user.UserAlreadyExistsException;
import ru.akvine.profiley.exceptions.user.UserNotFoundException;
import ru.akvine.profiley.repository.UserRepository;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.services.dto.user.UpdateUser;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.generators.UUIDGenerator;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String email, String password) {
        Asserts.isNotNull(email, "email is null");
        Asserts.isNotNull(password, "password is null");

        try {
            verifyExistsByEmail(email);
            throw new UserAlreadyExistsException("User with email = [" + email + "] already exists!");
        } catch (UserNotFoundException exception) {
            String hash = passwordEncoder.encode(password);

            UserEntity user = new UserEntity()
                    .setEmail(email)
                    .setHash(hash);
            user.setUuid(UUIDGenerator.uuidWithoutDashes());
            return new User(userRepository.save(user));
        }
    }

    @Override
    public User get(String uuid) {
        return new User(verifyExistsByUuid(uuid));
    }

    @Override
    public User update(UpdateUser updateUser) {
        Asserts.isNotNull(updateUser, "updateUser is null");

        UserEntity userToUpdate = verifyExistsByUuid(updateUser.getUserUuid());

        if (updateUser.getDisabledSystemDomains() != null &&
                userToUpdate.isDisabledSystemDomains() != updateUser.getDisabledSystemDomains()) {
            userToUpdate.setDisabledSystemDomains(updateUser.getDisabledSystemDomains());
        }

        if (updateUser.getDisabledSystemRules() != null &&
                userToUpdate.isDisabledSystemRules() != updateUser.getDisabledSystemRules()) {
            userToUpdate.setDisabledSystemRules(updateUser.getDisabledSystemRules());
        }

        if (updateUser.getLanguage() != null &&
                !userToUpdate.getLanguage().equals(updateUser.getLanguage())) {
            userToUpdate.setLanguage(updateUser.getLanguage());
        }

        userToUpdate.setUpdatedDate(new Date());
        return new User(userRepository.save(userToUpdate));
    }

    public UserEntity verifyExistsByEmail(String email) {
        Asserts.isNotNull(email, "email is null");
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by email = [" + email + "]"));
    }

    @Override
    public UserEntity verifyExistsByUuid(String uuid) {
        Asserts.isNotNull(uuid, "uuid is null");
        return userRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found by uuid = [" + uuid + "]"));
    }
}
