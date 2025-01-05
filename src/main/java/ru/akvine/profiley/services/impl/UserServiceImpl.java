package ru.akvine.profiley.services.impl;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.exceptions.UserAlreadyExistsException;
import ru.akvine.profiley.exceptions.UserNotFoundException;
import ru.akvine.profiley.repository.UserRepository;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.utils.UUIDGenerator;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String email, String password) {
        Preconditions.checkNotNull(email, "email is null");
        Preconditions.checkNotNull(password, "password is null");

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

    public UserEntity verifyExistsByEmail(String email) {
        Preconditions.checkNotNull(email, "email is null");
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by email = [" + email + "]"));
    }

    @Override
    public UserEntity verifyExistsByUuid(String uuid) {
        Preconditions.checkNotNull(uuid, "uuid is null");
        return userRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found by uuid = [" + uuid + "]"));
    }
}
