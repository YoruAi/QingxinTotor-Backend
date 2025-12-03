package com.yoru.qingxintutor.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.regex.Pattern;

@Documented
@Constraint(validatedBy = ValidUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "Username can only contain letters, digits, and underscores (a-z, A-Z, 0-9, _)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class ValidUsernameValidator implements ConstraintValidator<Username, String> {
    // 只允许字母、数字、下划线，且长度至少1
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }
}