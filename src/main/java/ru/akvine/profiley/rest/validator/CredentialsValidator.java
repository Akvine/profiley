package ru.akvine.profiley.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.utils.Asserts;


@Component
@RequiredArgsConstructor
public class CredentialsValidator {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public User validateCredentials(String email, String password) {
        Asserts.isNotNull(email, "email is null");
        Asserts.isNotNull(password, "password is null");

        UserEntity userEntity = userService.verifyExistsByEmail(email);
        if (!passwordEncoder.matches(password, userEntity.getHash())) {
            throw new BadCredentialsException("Bad credentials!");
        }

        return new User(userEntity);
    }
}
