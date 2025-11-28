package com.yoru.qingxintutor.service;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.mapper.TeacherReviewMapper;
import com.yoru.qingxintutor.pojo.dto.request.ReviewCreateRequest;
import com.yoru.qingxintutor.pojo.dto.request.ReviewUpdateRequest;
import com.yoru.qingxintutor.pojo.entity.TeacherReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private TeacherReviewMapper reviewMapper;

    public List<TeacherReviewEntity> listAllByUserId(String userId) {
        return reviewMapper.findByUserId(userId);
    }

    public TeacherReviewEntity findById(String userId, Long id) {
        TeacherReviewEntity review = reviewMapper.findById(id)
                .orElseThrow(() -> new BusinessException("Review not found"));
        if (!userId.equals(review.getUserId()))
            throw new BusinessException("Review not found");
        return review;
    }

    @Transactional
    public TeacherReviewEntity create(String userId, ReviewCreateRequest request) {
        // 检查是否已存在对该教师的评价（唯一性约束）
        if (reviewMapper.findByUserIdAndTeacherId(userId, request.getTeacherId()).isPresent()) {
            throw new BusinessException("Teacher has been rated by you");
        }

        // 创建并保存实体
        TeacherReviewEntity entity = TeacherReviewEntity.builder()
                .userId(userId)
                .teacherId(request.getTeacherId())
                .title(request.getTitle())
                .content(request.getContent())
                .rating(request.getRating())
                .createTime(LocalDateTime.now())
                .build();
        reviewMapper.insert(entity);

        return entity;
    }

    public TeacherReviewEntity update(String userId, Long id, ReviewUpdateRequest request) {
        TeacherReviewEntity review = reviewMapper.findById(id)
                .orElseThrow(() -> new BusinessException("Review not found"));
        if (!userId.equals(review.getUserId()))
            throw new BusinessException("Review not found");

        // 更新实体
        TeacherReviewEntity entity = TeacherReviewEntity.builder()
                .id(id)
                .title(request.getTitle())
                .content(request.getContent())
                .rating(request.getRating())
                .build();
        reviewMapper.update(entity);

        return reviewMapper.findById(id)
                .orElseThrow(() -> new BusinessException("Review not found"));
    }

    public void deleteById(String userId, Long id) {
        TeacherReviewEntity review = reviewMapper.findById(id)
                .orElseThrow(() -> new BusinessException("Review not found"));
        if (!userId.equals(review.getUserId()))
            throw new BusinessException("Review not found");
        reviewMapper.deleteById(id);
    }
}
