package com.run.student.service;

import com.run.student.entity.Student;
import com.run.student.vo.StudentVo;

import java.util.List;

public interface StudentService{
    public List<StudentVo> list();
    public List<StudentVo> selectPage(Integer pageNum, Integer pageSize);
    public Integer getTotalNum();

    public int insert(Student student);

    public int deleteById(int id);

    public int updateById(Student student);

    public Student isExist(int id);
}
