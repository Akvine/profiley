package ru.akvine.profiley.constants;

public final class ErrorCodes {
    private ErrorCodes() throws IllegalAccessException {
        throw new IllegalAccessException("Calling " + ErrorCodes.class.getSimpleName() + " is prohibited!");
    }

    public static final String NO_SESSION = "no.session.error";

    public final static String GENERAL_ERROR = "general.error";
}
