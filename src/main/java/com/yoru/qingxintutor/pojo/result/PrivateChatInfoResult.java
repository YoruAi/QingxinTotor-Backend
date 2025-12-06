package com.yoru.qingxintutor.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateChatInfoResult {
    private Long id;
    private String studentId;
    private String studentName;
    private Long teacherId;
    private String teacherName;
}
