package ru.akvine.profiley.enums;

import org.apache.commons.lang3.StringUtils;

public enum ProcessState {
    CREATED,
    STARTED,
    COMPLETED_SUCCESSFUL,
    COMPLETED_WITH_ERROR;

    public static ProcessState from(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Value is blank!");
        }

        switch (value.toLowerCase()) {
            case "created" -> {
                return CREATED;
            }
            case "started" -> {
                return STARTED;
            }
            case "success" -> {
                return COMPLETED_SUCCESSFUL;
            }
            case "error" -> {
                return COMPLETED_WITH_ERROR;
            }
            default -> throw new UnsupportedOperationException("Process state = [" + value + "] is not supported!");
        }
    }
}
