package com.yoru.qingxintutor.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.mapper.TeacherMapper;
import com.yoru.qingxintutor.mapper.TeacherReviewMapper;
import com.yoru.qingxintutor.mapper.TeacherSubjectMapper;
import com.yoru.qingxintutor.pojo.dto.request.TeacherSearchRequest;
import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import com.yoru.qingxintutor.pojo.entity.TeacherReviewEntity;
import com.yoru.qingxintutor.pojo.result.TeacherInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherReviewMapper teacherReviewMapper;

    @Autowired
    private TeacherSubjectMapper teacherSubjectMapper;

    /**
     * 根据id查询该教师所有评论
     */
    public PageInfo<TeacherReviewEntity> getReviewsById(Long teacherId, Integer pageNum, Integer pageSize)
            throws BusinessException {
        if (!teacherMapper.existsById(teacherId))
            throw new BusinessException("Teacher not found");
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherReviewEntity> list = teacherReviewMapper.findByTeacherId(teacherId);
        return new PageInfo<>(list);
    }

    /**
     * 根据id查询该教师所有科目
     */
    public List<SubjectEntity> getSubjectsById(Long teacherId) throws BusinessException {
        if (!teacherMapper.existsById(teacherId))
            throw new BusinessException("Teacher not found");
        return teacherSubjectMapper.findByTeacherId(teacherId);
    }

    /**
     * 查询所有优秀 (avg_rating>=4.00) 教师信息
     */
    public PageInfo<TeacherInfoResult> listExcellent(Integer pageNum, Integer pageSize) {
        return queryPageByIds(teacherMapper::findExcellentIds, pageNum, pageSize);
    }

    /**
     * 查询所有教师信息
     */
    public PageInfo<TeacherInfoResult> listAll(Integer pageNum, Integer pageSize) {
        return queryPageByIds(teacherMapper::findAllIds, pageNum, pageSize);
    }

    /**
     * 根据搜索条件查询教师信息
     */
    public PageInfo<TeacherInfoResult> search(TeacherSearchRequest request, Integer pageNum, Integer pageSize) {
        return queryPageByIds(() -> teacherMapper.findIdsByCriteria(request), pageNum, pageSize);
    }


    private PageInfo<TeacherInfoResult> queryPageByIds(
            Supplier<List<Long>> idSupplier,
            Integer pageNum, Integer pageSize) {
        // 1. 分页查 ID
        Page<Long> idPage = PageHelper.startPage(pageNum, pageSize);
        List<Long> teacherIds = idSupplier.get();
        long total = idPage.getTotal();

        // 2. 查详情
        List<TeacherInfoResult> teachers = teacherIds.isEmpty()
                ? Collections.emptyList()
                : teacherMapper.findByIds(teacherIds);

        // 3. 构造分页结果
        Page<TeacherInfoResult> page = new Page<>(pageNum, pageSize);
        page.addAll(teachers);
        page.setTotal(total);
        return new PageInfo<>(page);
    }
}
