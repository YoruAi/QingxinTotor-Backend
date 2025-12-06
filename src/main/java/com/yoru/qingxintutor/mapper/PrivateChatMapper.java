package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.PrivateChatEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PrivateChatMapper {
    /**
     * 根据学生ID和老师ID查找会话
     */
    Optional<PrivateChatEntity> findByStudentAndTeacher(@Param("userId") String userId, @Param("teacherId") Long teacherId);

    /**
     * 根据学生ID查找会话
     */
    List<PrivateChatEntity> findByStudent(@Param("userId") String userId);

    /**
     * 根据老师ID查找会话
     */
    List<PrivateChatEntity> findByTeacher(@Param("teacherId") Long teacherId);

    /**
     * 插入新会话
     */
    void insert(PrivateChatEntity chat);

    /**
     * 根据ID查询会话
     */
    Optional<PrivateChatEntity> findById(Long id);
}
