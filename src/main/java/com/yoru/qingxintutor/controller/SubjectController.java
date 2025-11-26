package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import com.yoru.qingxintutor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/all")
    public ApiResult<List<SubjectEntity>> listAllSubjects() {
        return ApiResult.success(subjectService.listAll());
    }

    @GetMapping("/search")
    public ApiResult<SubjectEntity> listAllSubjects(@RequestParam String subjectName)
            throws BusinessException {
        return ApiResult.success(subjectService.getByName(subjectName));
    }
}
