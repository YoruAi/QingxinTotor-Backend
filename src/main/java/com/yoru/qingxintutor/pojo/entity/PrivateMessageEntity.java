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
public class PrivateMessageEntity {
    private Long id;
    private Long chatId;
    private String content;
    private SenderType sender;
    private LocalDateTime createTime;

    public enum SenderType {
        TEACHER, STUDENT
    }
}
