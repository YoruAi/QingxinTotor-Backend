package com.yoru.qingxintutor.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVoucherEntity {
    private Long id;                    // BIGINT AUTO_INCREMENT PRIMARY KEY
    @JsonIgnore
    private String userId;              // CHAR(36) NOT NULL, REFERENCES user(id)
    private BigDecimal amount;          // BIGINT NOT NULL
    @JsonIgnore
    private LocalDateTime createTime;
    private LocalDateTime expireTime;   // DATETIME NOT NULL
}
