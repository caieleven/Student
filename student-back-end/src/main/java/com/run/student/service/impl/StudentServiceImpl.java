package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public Page<Student> searchPage(Page<Student> page, QueryWrapper<Student> queryWrapper) {
        return studentMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int selectCount(QueryWrapper<Student> queryWrapper) {
        return studentMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer getTotalNum() {
        return studentMapper.selectCount(null);
    }

    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public int updateById(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public int deleteById(int id) {
        return studentMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Integer> sids) {
        return studentMapper.deleteBatchIds(sids);
    }

    @Override
    public Student isExist(int id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<StudentVo> getSpecialStudent(String sid, String studentName, String cid, String status){
        return studentVoMapper.getSpecialStudent(sid, studentName, cid, status);
    }

}
