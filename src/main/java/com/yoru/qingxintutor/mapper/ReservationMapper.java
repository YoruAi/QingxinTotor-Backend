package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.ReservationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {
    /**
     * 根据ID和用户ID查询（用于学生操作自己的预约）
     */
    ReservationEntity findByIdAndUserId(@Param("id") Long id, @Param("userId") String userId);

    /**
     * 根据ID和老师ID查询（用于老师操作自己的预约）
     */
    ReservationEntity findByIdAndTeacherId(@Param("id") Long id, @Param("teacherId") String teacherId);

    /**
     * 查询用户指定状态的预约
     */
    List<ReservationEntity> findByUserIdAndState(@Param("userId") String userId, @Param("state") String state);

    /**
     * 查询老师指定状态的预约
     */
    List<ReservationEntity> findByTeacherIdAndState(@Param("teacherId") String teacherId, @Param("state") String state);

    /**
     * 查询用户即将开始的已确认课程
     */
    List<ReservationEntity> findUpcomingByUserId(String userId);

    /**
     * 创建新预约
     */
    void insert(ReservationEntity reservation);

    /**
     * 更新预约状态
     */
    int updateState(@Param("id") Long id, @Param("state") String state);

    /**
     * 检查老师在指定时间段是否有冲突的已确认课程
     */
    boolean existsConflict(
            @Param("teacherId") String teacherId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 查询所有已过期且仍为 PENDING 的预约（用于定时取消）
     */
    List<ReservationEntity> getExpiredPendingReservations();
}
