package com.yoru.qingxintutor.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSubjectEntity {
    private String teacherId;       // CHAR(36), REFERENCES teacher(user_id)
    private Long subjectId;         // BIGINT, REFERENCES subject(id)
    private LocalDateTime createTime;
}