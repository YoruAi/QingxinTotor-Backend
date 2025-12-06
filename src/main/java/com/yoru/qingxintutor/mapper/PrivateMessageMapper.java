package com.yoru.qingxintutor.mapper;

import com.yoru.qingxintutor.pojo.entity.PrivateMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PrivateMessageMapper {
    /**
     * 插入消息
     */
    void insert(PrivateMessageEntity message);

    /**
     * 根据 chat_id 查询所有消息（按时间升序）
     */
    List<PrivateMessageEntity> findByChatId(@Param("chatId") Long chatId);

    /**
     * 根据ID查询消息
     */
    Optional<PrivateMessageEntity> findById(@Param("id") Long id);
}
