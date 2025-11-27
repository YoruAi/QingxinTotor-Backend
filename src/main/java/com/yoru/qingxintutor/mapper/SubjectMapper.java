package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SubjectMapper {

    /**
     * 根据科目 ID 查询科目
     */
    Optional<SubjectEntity> findById(@Param("id") Long id);

    /**
     * 根据科目名称精确查询科目
     */
    Optional<SubjectEntity> findBySubjectName(@Param("subjectName") String subjectName);

    /**
     * 查询所有科目，按 ID 升序排列
     */
    List<SubjectEntity> findAll();

    /**
     * 插入新科目，返回自动生成的 ID
     */
    int insert(SubjectEntity subject);

    /**
     * 更新科目信息
     */
    int update(SubjectEntity subject);

    /**
     * 查询科目名称对应id
     */
    List<Long> findIdsByNames(@Param("names") List<String> names);
}
