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
public class PrivateChatEntity {
    private Long id;
    private String userId;      // 学生ID
    private Long teacherId;     // 老师ID
    private LocalDateTime createTime;
}
