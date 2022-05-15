package com.run.student;

import com.run.student.entity.User;
import com.run.student.mapper.StudentVoMapper;
import com.run.student.mapper.UserMapper;
import com.run.student.vo.StudentVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudentApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentVoMapper studentVoMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testMapper(){
        StudentVo studentVo = studentVoMapper.getStudentById("19122206");
        System.out.print(studentVo.toString());
    }

}
