package ru.akvine.profiley.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RestMethods {
    public interface Security {
        String REGISTRATION_ENDPOINT = "security/registration";
        String AUTHENTICATION_ENDPOINT = "security/auth";
        String LOGOUT_ENDPOINT = "security/logout";
    }

    public interface Domain {
        String DOMAIN_ENDPOINT = "domains";
    }
}
