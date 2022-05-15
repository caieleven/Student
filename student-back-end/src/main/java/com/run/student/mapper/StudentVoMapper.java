package com.run.student.mapper;

import com.run.student.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentVoMapper {
    StudentVo getStudentById(String sid);
}
