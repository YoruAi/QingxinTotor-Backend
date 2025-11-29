package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.UserVoucherEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserVoucherMapper {
    Optional<UserVoucherEntity> findById(Long id);

    List<UserVoucherEntity> findByUserId(String userId);

    List<UserVoucherEntity> findValidByUserId(String userId);

    void insert(UserVoucherEntity voucher);

    void deleteExpired();

    void deleteById(Long id);
}
