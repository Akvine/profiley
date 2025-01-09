package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.AuthenticationService;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.User;
import ru.akvine.profiley.utils.Asserts;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public User authenticate(String email, String password) {
        Asserts.isNotNull(email, "email is null");
        Asserts.isNotNull(password, "password is null");

        UserEntity userEntity = userService.verifyExistsByEmail(email);
        if (!passwordEncoder.matches(password, userEntity.getHash())) {
            throw new BadCredentialsException("Bad credentials!");
        }

        return new User(userEntity);
    }
}
