package ru.akvine.profiley.services;

import ru.akvine.profiley.services.domain.User;

public interface AuthenticationService {
    User authenticate(String email, String password);
}
