package com.yoru.qingxintutor.config.convert;

import com.yoru.qingxintutor.pojo.entity.TeacherEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeacherGenderConvert implements Converter<String, TeacherEntity.Gender> {
    @SuppressWarnings("NullableProblems")
    @Override
    public TeacherEntity.Gender convert(String source) {
        //noinspection ConstantValue
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            return TeacherEntity.Gender.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }
}
