package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import com.yoru.qingxintutor.service.SubjectService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/{id}")
    public ApiResult<SubjectEntity> getSubject(@PathVariable("id")
                                               @Min(value = 1, message = "Id must be a positive number")
                                               Long id) {
        return ApiResult.success(subjectService.getById(id));
    }

    @GetMapping("/all")
    public ApiResult<List<SubjectEntity>> listAllSubjects() {
        return ApiResult.success(subjectService.listAll());
    }

    @GetMapping("/search")
    public ApiResult<SubjectEntity> listAllSubjects(@RequestParam String subjectName) {
        return ApiResult.success(subjectService.getByName(subjectName));
    }

    @GetMapping("/teacher/{teacherId}")
    public ApiResult<List<SubjectEntity>> getTeacherSubjects(@PathVariable("teacherId")
                                                             @Min(value = 1, message = "TeacherId must be a positive number")
                                                             Long teacherId) {
        return ApiResult.success(subjectService.getSubjectsByTeacherId(teacherId));
    }
}
