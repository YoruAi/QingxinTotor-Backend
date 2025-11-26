package com.yoru.qingxintutor.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherEntity {
    private Long id;                    // BIGINT PRIMARY KEY AUTO_INCREMENT
    private String phone;               // VARCHAR(20) NOT NULL UNIQUE
    private String nickname;            // VARCHAR(50)
    private String name;                // VARCHAR(50) NOT NULL
    private Gender gender;              // ENUM('MALE', 'FEMALE') NOT NULL
    private LocalDate birthDate;        // DATE NOT NULL
    private String icon;                // VARCHAR(255)
    private String address;             // VARCHAR(255)
    private String teachingExperience;  // TEXT
    private String description;         // TEXT
    private Integer grade;              // TINYINT UNSIGNED (1~9)
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;

    public enum Gender {
        MALE,
        FEMALE
    }
}
