package com.yoru.qingxintutor.controller;

import com.github.pagehelper.PageInfo;
import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.dto.request.TeacherSearchRequest;
import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import com.yoru.qingxintutor.pojo.entity.TeacherReviewEntity;
import com.yoru.qingxintutor.pojo.result.TeacherInfoResult;
import com.yoru.qingxintutor.service.TeacherService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/excellent")
    public ApiResult<PageInfo<TeacherInfoResult>> listExcellentTeachers(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                        @RequestParam(defaultValue = "6") Integer pageSize) {
        return ApiResult.success(teacherService.listExcellent(pageNum, pageSize));
    }

    @GetMapping("/all")
    public ApiResult<PageInfo<TeacherInfoResult>> listAllTeachers(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                  @RequestParam(defaultValue = "6") Integer pageSize) {
        return ApiResult.success(teacherService.listAll(pageNum, pageSize));
    }

    @GetMapping("/search")
    public ApiResult<PageInfo<TeacherInfoResult>> listTeachersByArgument(
            @Valid @ModelAttribute TeacherSearchRequest teacherSearchRequest,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "6") Integer pageSize
    ) {
        return ApiResult.success(teacherService.search(teacherSearchRequest, pageNum, pageSize));
    }

    @GetMapping("/{teacherId}/reviews")
    public ApiResult<PageInfo<TeacherReviewEntity>> getTeacherReviews(@PathVariable("teacherId")
                                                                      @Min(value = 1, message = "TeacherId must be a positive number")
                                                                      Long teacherId,
                                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                                      @RequestParam(defaultValue = "6") Integer pageSize)
            throws BusinessException {
        return ApiResult.success(teacherService.getReviewsById(teacherId, pageNum, pageSize));
    }

    @GetMapping("/{teacherId}/subjects")
    public ApiResult<List<SubjectEntity>> getTeacherReviews(@PathVariable("teacherId")
                                                            @Min(value = 1, message = "TeacherId must be a positive number")
                                                            Long teacherId)
            throws BusinessException {
        return ApiResult.success(teacherService.getSubjectsById(teacherId));
    }
}
