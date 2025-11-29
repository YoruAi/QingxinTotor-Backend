package com.yoru.qingxintutor.config.convert;

import com.yoru.qingxintutor.enums.NotificationType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationTypeConvert implements Converter<String, NotificationType> {
    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public NotificationType convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            return NotificationType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid notification type value");
        }
    }
}