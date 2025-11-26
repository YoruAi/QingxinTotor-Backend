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
public class TeacherReviewEntity {
    private Long id;                // BIGINT AUTO_INCREMENT PRIMARY KEY
    private String userId;          // CHAR(36) NOT NULL, REFERENCES user(id)
    private Long teacherId;       // CHAR(36) NOT NULL, REFERENCES teacher(user_id)
    private Integer rating;         // TINYINT NOT NULL (1~5)
    private String title;           // VARCHAR(100)
    private String content;         // TEXT
    private LocalDateTime createTime;
}
