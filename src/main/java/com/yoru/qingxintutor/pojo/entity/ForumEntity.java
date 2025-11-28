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
public class ForumEntity {
    private Long id;                // BIGINT AUTO_INCREMENT PRIMARY KEY
    private String name;            // VARCHAR(50) NOT NULL UNIQUE
    private String description;     // VARCHAR(255)
    private LocalDateTime createTime;
}
