package com.yoru.qingxintutor.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotificationType {
    GLOBAL,
    PERSONAL;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static NotificationType fromString(@JsonProperty String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return NotificationType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid notification type value");
        }
    }
}
