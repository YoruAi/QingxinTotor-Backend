package com.yoru.qingxintutor.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmailPurpose {
    REGISTER,
    LOGIN,
    RESET_PASSWORD;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EmailPurpose fromString(@JsonProperty String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return EmailPurpose.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }
}
