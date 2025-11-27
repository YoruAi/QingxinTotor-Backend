package com.yoru.qingxintutor.pojo.dto.request;

import com.yoru.qingxintutor.annotation.Phone;
import com.yoru.qingxintutor.pojo.entity.TeacherEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherUpdateRequest {
    @Phone
    private String phone;

    @Size(min = 1, max = 50, message = "Nickname must be between 1 and 50 characters")
    private String nickname;

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    private TeacherEntity.Gender gender;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Size(min = 1, max = 255, message = "Address must be between 1 and 255 characters")
    private String address;

    @Size(min = 1, max = 1000, message = "Teaching experience must be between 1 and 1000 characters")
    private String teachingExperience;

    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    private String description;

    @Min(value = 1, message = "Grade must be between 1 and 9")
    @Max(value = 9, message = "Grade must be between 1 and 9")
    private Integer grade;

    private List<String> subjectNames;
}
