package com.yoru.qingxintutor.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectEntity {
    private Long id;                // BIGINT AUTO_INCREMENT PRIMARY KEY
    private String subjectName;     // VARCHAR(50) NOT NULL UNIQUE
    private String description;     // VARCHAR(255)
}
