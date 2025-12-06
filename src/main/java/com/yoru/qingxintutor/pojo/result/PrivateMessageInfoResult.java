package com.yoru.qingxintutor.pojo.result;

import com.yoru.qingxintutor.pojo.entity.PrivateMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateMessageInfoResult {
    private Long id;
    private PrivateChatInfoResult chat;
    private String content;
    private PrivateMessageEntity.SenderType sender;
    private LocalDateTime createTime;
}
