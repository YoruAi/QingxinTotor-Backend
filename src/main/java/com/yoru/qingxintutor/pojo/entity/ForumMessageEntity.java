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
public class ForumMessageEntity {
    private Long id;                // BIGINT AUTO_INCREMENT PRIMARY KEY
    private Long forumId;           // BIGINT NOT NULL, REFERENCES forum(id)
    private String userId;          // CHAR(36), REFERENCES user(id)
    private String content;         // TEXT NOT NULL
    private LocalDateTime createTime;
}
