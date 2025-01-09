package ru.akvine.profiley.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.akvine.profiley.exceptions.AssertException;
import ru.akvine.profiley.services.impl.AuthenticationServiceImpl;

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
}
