package com.run.student.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.student.entity.Student;
import com.run.student.utils.StudentQuery;
import com.run.student.vo.StudentVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentService {
    public List<StudentVo> list();

    public List<StudentVo> selectPage(Integer pageNum, Integer pageSize);

    public Page<Student> searchPage(Page<Student> page, QueryWrapper<Student> queryWrapper);

    public int selectCount(QueryWrapper<Student> queryWrapper);

    public Integer getTotalNum();

    public int insert(Student student);

    public int deleteById(int id);

    public int updateById(Student student);

    public Student isExist(int id);

    public List<StudentVo> getSpecialStudent(String sid, String studentName, String cid, String status);

    public int deleteBatch(List<Integer> sids);

    /**
     * 自定义sql添加wrapper
     * @param studentQuery 自定义query
     * @return
     */
    public List<StudentVo> queryPage(StudentQuery studentQuery);

//    public List<StudentVo> testMongo(StudentQuery studentQuery);

}
