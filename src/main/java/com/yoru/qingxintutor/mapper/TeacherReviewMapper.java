package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.TeacherReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Mapper
public interface TeacherReviewMapper {

    /**
     * 根据评论 ID 查询评论
     */
    Optional<TeacherReviewEntity> findById(@Param("id") Long id);

    /**
     * 根据教师 ID 查询所有评论（按时间倒序）
     */
    List<TeacherReviewEntity> findByTeacherId(@Param("teacherId") Long teacherId);

    /**
     * 根据用户 ID 查询评论（用于展示用户所有评论中的教师评论部分）
     */
    List<TeacherReviewEntity> findByUserId(@Param("userId") String userId);

    /**
     * 根据用户 ID 和教师 ID 查询评论（用于判断是否已评价）
     */
    Optional<TeacherReviewEntity> findByUserIdAndTeacherId(
            @Param("userId") String userId, @Param("teacherId") Long teacherId
    );

    /**
     * 插入一条新评论
     */
    int insert(TeacherReviewEntity review);

    /**
     * 计算某位教师的平均评分（保留两位小数，无评论时返回 0.00）
     */
    BigDecimal getAverageRatingByTeacherId(@Param("teacherId") Long teacherId);

    /**
     * 根据 ID 删除
     */
    void deleteById(Long id);

    int update(TeacherReviewEntity review);
}
