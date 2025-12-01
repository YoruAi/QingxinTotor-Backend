package com.yoru.qingxintutor.pojo.result;

import com.yoru.qingxintutor.pojo.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationInfoResult {
    private Long id;
    private String userId;
    private String username;
    private Long teacherId;
    private String teacherName;
    private String subjectName;
    private LocalDateTime startTime;
    private Integer duration;
    private ReservationEntity.State state;
}
