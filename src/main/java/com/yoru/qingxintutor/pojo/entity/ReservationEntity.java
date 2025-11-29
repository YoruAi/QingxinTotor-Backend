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
public class ReservationEntity {
    private Long id;                // BIGINT AUTO_INCREMENT PRIMARY KEY
    private String userId;          // CHAR(36) NOT NULL, REFERENCES user(id)
    private String teacherId;       // CHAR(36) NOT NULL, REFERENCES teacher(user_id)
    private Long subjectId;         // BIGINT NOT NULL, REFERENCES subject(id)
    private LocalDateTime startTime; // DATETIME NOT NULL
    private Integer duration;       // INT NOT NULL, 单位：分钟
    private String state;           // VARCHAR(20) NOT NULL (e.g., PENDING, CONFIRMED, CANCELLED)
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
