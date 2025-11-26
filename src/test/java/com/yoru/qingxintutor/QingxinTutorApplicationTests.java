package com.yoru.qingxintutor;

import com.yoru.qingxintutor.mapper.UserMapper;
import com.yoru.qingxintutor.pojo.entity.UserEntity;
import com.yoru.qingxintutor.utils.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class QingxinTutorApplicationTests {

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void insertUserTest() {
        LocalDateTime now = LocalDateTime.now();
        userMapper.insert(
                UserEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .username("陈芳")
                        .nickname("陈老师")
                        .email("QXTutorChenFang@163.com")
                        .passwdHash(passwordEncoder.encode("qwe123@AAA"))
                        .role(UserEntity.Role.TEACHER)
                        .address("深圳市南山区")
                        .createTime(now)
                        .updateTime(now)
                        .build()
        );
    }

    @Test
    void emailTest() {
        emailUtils.sendLoginCode("2085509323@qq.com", "123123");
    }
}
