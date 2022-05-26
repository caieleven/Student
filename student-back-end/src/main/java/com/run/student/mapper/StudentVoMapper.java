package com.run.student.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.Student;
import com.run.student.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentVoMapper {
    StudentVo getStudentById(String sid);
    List<StudentVo> getAllStudents();
    List<StudentVo> selectPage(Integer pageNum, Integer pageSize);

    List<StudentVo> getSpecialStudent(String sid, String studentName, String cid, String status);
}
