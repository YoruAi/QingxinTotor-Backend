package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.ForumEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ForumMapper {
    Optional<ForumEntity> findById(@Param("id") Long id);

    Optional<ForumEntity> findByName(@Param("name") String name);

    List<ForumEntity> findAll();

    int insert(ForumEntity forum);
}
