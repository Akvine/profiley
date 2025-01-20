package ru.akvine.profiley.constants;

public final class ErrorCodes {
    private ErrorCodes() throws IllegalAccessException {
        throw new IllegalAccessException("Calling " + ErrorCodes.class.getSimpleName() + " is prohibited!");
    }

    public static final String NO_SESSION = "no.session.error";

    public final static String GENERAL_ERROR = "general.error";

    public final static String RESOURCE_NOT_FOUND_ERROR = "resource.notFound.error";
    public final static String JSON_BODY_INVALID_ERROR = "json.body.invalid.error";

    public interface Validation {
        String FIELD_NOT_PRESENTED_ERROR = "field.not.presented.error";
        String EMAIL_INVALID_ERROR = "email.invalid.error";
        String PASSWORD_INVALID_ERROR = "password.invalid.error";
        String DICTIONARY_WORDS_COUNT_ERROR = "dictionary.words.count.error";
        String FILE_EXTENSION_NOT_SUPPORTED = "file.extension.not.supported.error";
        String FILE_TYPE_NOT_VALID_ERROR = "file.type.not.valid.error";
    }

    public interface User {
        String USER_NOT_FOUND_ERROR = "user.notFound.error";
        String BAD_CREDENTIALS_ERROR = "bad.credentials.error";
    }

    public interface Domain {
        String DOMAIN_ALREADY_EXISTS_ERROR = "domain.alreadyExists.error";
    }

    public interface Dictionary {
        String DICTIONARY_NOT_FOUND_ERROR = "dictionary.notFound.error";
        String DICTIONARY_MAX_COUNT_ERROR = "dictionary.max.count.error";
    }
}
