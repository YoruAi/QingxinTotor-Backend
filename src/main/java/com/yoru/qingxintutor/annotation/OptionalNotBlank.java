package com.yoru.qingxintutor.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = OptionalNotBlankValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptionalNotBlank {
    String message() default "must not be blank if present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class OptionalNotBlankValidator implements ConstraintValidator<OptionalNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.isBlank();
    }
}