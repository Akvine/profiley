package ru.akvine.profiley.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.akvine.profiley.exceptions.AssertException;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticationServiceImpl tests")
public class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl service;

    @Test
    @DisplayName("Throw exception if email is null")
    void throw_exception_if_email_is_null() {
        assertThatThrownBy(() -> service.authenticate(null, "some password"))
                .isInstanceOf(AssertException.class)
                .hasMessage("email is null");

    }

    @Test
    @DisplayName("Throw exception if password is null")
    void throw_exception_if_password_is_null() {
        assertThatThrownBy(() -> service.authenticate("testemail@gmail.com", null))
                .isInstanceOf(AssertException.class)
                .hasMessage("password is null");
    }

    @Test
    @DisplayName("Throw exception if password is incorrect")
    void throw_exception_if_user_not_found() {
        String email = "testemail@gmail.com";
        String password = "testPassword";
        String userHash = "some hash";
        UserEntity userFromDb = new UserEntity()
                .setHash(userHash)
                .setEmail(email);
        Mockito
                .when(userService.verifyExistsByEmail(email))
                .thenReturn(userFromDb);
        Mockito
                .when(passwordEncoder.matches(password, userHash))
                .thenReturn(false);

        assertThatThrownBy(() -> service.authenticate(email, password))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Bad credentials!");
    }

    @Test
    @DisplayName("Return user if password is correct")
    void return_user_if_password_is_correct() {
        String email = "testemail@gmail.com";
        String password = "testPassword";
        String userHash = "some hash";
        UserEntity userFromDb = new UserEntity()
                .setHash(userHash)
                .setEmail(email);
        Mockito
                .when(userService.verifyExistsByEmail(email))
                .thenReturn(userFromDb);
        Mockito
                .when(passwordEncoder.matches(password, userHash))
                .thenReturn(true);
        User expectedUser = new User(userFromDb);

        User result = service.authenticate(email, password);

        assertThat(expectedUser).isNotNull();
        assertThat(result).isEqualTo(expectedUser);
    }
}
