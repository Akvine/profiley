package ru.akvine.profiley.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.akvine.profiley.exceptions.user.UserAlreadyExistsException;
import ru.akvine.profiley.exceptions.user.UserNotFoundException;
import ru.akvine.profiley.repository.UserRepository;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.utils.UUIDGenerator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl tests")
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    @DisplayName("Throw UserAlreadyExistsException cause user exists in db")
    void throw_exception_cause_user_exists_in_db() {
        String testEmail = "email@gmail.com";
        String testPassword = "testPassword";
        UserEntity testUser = new UserEntity()
                .setEmail(testEmail)
                .setHash("test hash")
                .setUuid("test uuid");
        Mockito
                .when(userRepository.findByEmail(testEmail))
                .thenReturn(Optional.of(testUser));

        assertThatThrownBy(() -> service.create(testEmail, testPassword))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Throw UserNotFoundException cause user user not found by email")
    void throw_exception_cause_user_not_found_by_email() {
        String testEmail = "email@gmail.com";
        Mockito
                .when(userRepository.findByEmail(testEmail))
                .thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> service.verifyExistsByEmail(testEmail))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Success return user by email")
    void success_return_user_by_email() {
        String testEmail = "email@gmail.com";
        UserEntity expected = new UserEntity().setEmail(testEmail);
        Mockito
                .when(userRepository.findByEmail(testEmail))
                .thenReturn(Optional.of(expected));

        UserEntity result = service.verifyExistsByEmail(testEmail);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(testEmail);
    }
    @Test
    @DisplayName("Throw UserNotFoundException user cause not found in db")
    void throw_exception_cause_user_not_found_by_uuid() {
        String uuid = UUIDGenerator.uuidWithoutDashes();
        Mockito
                .when(userRepository.findByUuid(uuid))
                .thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> service.verifyExistsByUuid(uuid))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Success return user by uuid")
    void success_return_user_by_uuid() {
        String uuid = UUIDGenerator.uuidWithoutDashes();
        UserEntity expected = new UserEntity().setUuid(uuid);
        Mockito
                .when(userRepository.findByUuid(uuid))
                .thenReturn(Optional.of(expected));

        UserEntity result = service.verifyExistsByUuid(uuid);

        assertThat(result).isNotNull();
        assertThat(result.getUuid()).isEqualTo(uuid);
    }

}
