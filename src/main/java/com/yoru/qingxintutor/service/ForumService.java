package com.yoru.qingxintutor.service;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.mapper.ForumMapper;
import com.yoru.qingxintutor.pojo.entity.ForumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    private ForumMapper forumMapper;

    public List<ForumEntity> listAll() {
        return forumMapper.findAll();
    }

    public ForumEntity findById(Long forumId) {
        return forumMapper.findById(forumId)
                .orElseThrow(() -> new BusinessException("Forum not found"));
    }
}
