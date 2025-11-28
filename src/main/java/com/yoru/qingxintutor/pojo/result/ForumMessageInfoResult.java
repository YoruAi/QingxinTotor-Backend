package com.yoru.qingxintutor.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumMessageInfoResult {
    private Long id;
    private Long forumId;
    private String forumName;
    private String userId;
    private String username;
    private String content;
    private LocalDateTime createTime;
}
