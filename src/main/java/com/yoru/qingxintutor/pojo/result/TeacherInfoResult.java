package com.yoru.qingxintutor.pojo.result;

import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import com.yoru.qingxintutor.pojo.entity.TeacherEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherInfoResult {
    TeacherEntity teacher;
    List<SubjectEntity> subjects;
    BigDecimal avgRating;
}
