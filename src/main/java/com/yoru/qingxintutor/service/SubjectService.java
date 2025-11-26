package com.yoru.qingxintutor.service;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.mapper.SubjectMapper;
import com.yoru.qingxintutor.pojo.entity.SubjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    public List<SubjectEntity> listAll() {
        return subjectMapper.findAll();
    }

    public SubjectEntity getByName(String subjectName) throws BusinessException {
        Optional<SubjectEntity> subject = subjectMapper.findBySubjectName(subjectName);
        if (subject.isEmpty())
            throw new BusinessException("Subject not found");
        return subject.get();
    }
}
