package com.run.student.service.impl;

import com.run.student.entity.Student;
import com.run.student.mapper.StudentMapper;
import com.run.student.mapper.StudentVoMapper;
import com.run.student.service.StudentService;
import com.run.student.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentVoMapper studentVoMapper;

    @Autowired
    @Resource
    StudentMapper studentMapper;

    @Override
    public List<StudentVo> list() {
        return studentVoMapper.getAllStudents();
    }

    @Override
    public List<StudentVo> selectPage(Integer pageNum, Integer pageSize) {
        return studentVoMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public Integer getTotalNum() {
        return studentMapper.selectCount(null);
    }
}
