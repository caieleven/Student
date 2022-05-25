package com.run.student.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.student.entity.Student;
import com.run.student.vo.StudentVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentService{
    public List<StudentVo> list();

    public List<StudentVo> selectPage(Integer pageNum, Integer pageSize);
    public Page<Student> searchPage(Page<Student> page, QueryWrapper<Student> queryWrapper);
    public int selectCount(QueryWrapper<Student> queryWrapper);
    public Integer getTotalNum();

    public int insert(Student student);

    public int deleteById(int id);

    public int updateById(Student student);

    public Student isExist(int id);

    public List<StudentVo> getSpecialStudent(String sid, String studentName);

//    public Integer getTotalSpecialStudent(String sid, String studentName);

    public List<StudentVo> getStudentByClass(Integer cid, Integer pageNum, Integer pageSize);

//    public Integer getTotalStudentByClass(Integer cid);
}
