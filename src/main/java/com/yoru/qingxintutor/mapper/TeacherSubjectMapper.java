package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherSubjectMapper {

    /**
     * 查询某教师所授的所有科目
     */
    List<SubjectEntity> findByTeacherId(@Param("teacherId") Long teacherId);

    /**
     * 检查教师与科目之间的关联是否存在
     */
    boolean exists(@Param("teacherId") Long teacherId, @Param("subjectId") Long subjectId);

    /**
     * 插入一条教师-科目关联
     */
    int insert(@Param("teacherId") Long teacherId, @Param("subjectId") Long subjectId);

    /**
     * 删除某教师的所有科目关联
     */
    int deleteByTeacherId(@Param("teacherId") Long teacherId);

    /**
     * 删除某科目的所有教师关联
     */
    int deleteBySubjectId(@Param("subjectId") Long subjectId);

    /**
     * 删除指定的教师-科目关联
     */
    int delete(@Param("teacherId") Long teacherId, @Param("subjectId") Long subjectId);
}
